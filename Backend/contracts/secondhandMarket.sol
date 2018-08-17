pragma solidity ^0.4.16;

import "./secondhandProduct.sol";
import "./ducktoken.sol";

contract SecondhandMarket {
    uint256 public totalProduct;
    uint256 public totalBarter;
    
    SecondhandProduct secondhandProduct = SecondhandProduct(0x2f57df7c57d6e0985b01fcbb71caa4f9d6b381ad);
    DuckToken ducktoken = DuckToken(0x38682e897ee39fbe51cd88e4421439ca518759c1);
    
    function SecondhandMarket (
        uint256 productNum,
        uint256 barterNum
    ) public {
        totalProduct = productNum;
        totalBarter = barterNum;
    }
    
    function registerProduct(bytes32 sn) public {
        require(totalProduct + 1 > totalProduct);
        secondhandProduct.registerProductOwnership(sn, msg.sender);
        totalProduct += 1;
    }
    function barter (bytes32 sn, address to, uint256 price) public {
        require(totalBarter + 1 > totalBarter);
        secondhandProduct.transferProductOwnership(sn, to, price);
        ducktoken.transferFromGranters(msg.sender, to, price);
        totalBarter += 1;
    }
}