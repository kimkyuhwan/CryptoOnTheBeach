package com.gyuhwan.android.blockchain.contract;

import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.math.BigInteger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SimpleStroageTranscationManager {

    private SimpleStorage contract;
    private String value;
    static private SimpleStroageTranscationManager instance;

   private SimpleStroageTranscationManager(){
       generateContractExecute();
   }

    public static SimpleStroageTranscationManager getInstance() {
       if(instance==null){
           instance=new SimpleStroageTranscationManager();
       }
        return instance;
    }

    public void generateContractExecute(){
        rx.Observable generateObs=rx.Observable.create(subscriber -> generateContract()).subscribeOn(Schedulers.newThread());
        generateObs.subscribe();
    }

    public void setContractValueExecute(String value){
        rx.Observable observableSet= rx.Observable.create(subscriber -> {
            setValue(value);
        }).subscribeOn(Schedulers.newThread());
        observableSet.subscribe();
    }
    public void getContractValueAndSetTextView(TextView tv){
        io.reactivex.Observable observableGet=io.reactivex.Observable.just("")
                .observeOn(io.reactivex.schedulers.Schedulers.computation())
                .map(v-> getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(v-> tv.setText(v.toString()));
        observableGet.subscribe();
    }

    public void generateContract(){
        Web3j web3j = Web3jFactory.build(new HttpService("http://165.246.223.55:8545"));
        Credentials credentials = Credentials.create("0xbdd1f043b1111541a21d3eb844d4424c290f3493530dcf2650b3b8b84f090bd4");
        try {
            contract = SimpleStorage.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setValue(String value){
        BigInteger temp = new BigInteger(value);
        try {
            contract.set(temp).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(){
        BigInteger val= null;
        try {
            val = contract.get().send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val.toString();
    }
}
