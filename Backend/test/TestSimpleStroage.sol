pragma solidity ^0.4.17;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/Sample.sol";

contract TestSimpleStroage {
    function testSimpleStroage() public {
            SimpleStorage ss = new SimpleStorage();

            uint expected = 4;
            ss.set(expected);
            Assert.equal(ss.get(), expected, "value equal test");
            
    }
}