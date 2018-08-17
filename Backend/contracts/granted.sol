pragma solidity ^0.4.16;

import "./owned.sol";

contract Granted is Owned {
    mapping (address => bool) public granters;
    
    // function granted() public {}
    
    modifier onlyGranters {
        require(granters[msg.sender] == true);
        _;
    }
    
    function addGranter(address newGranter) onlyOwner public {
        granters[newGranter] = true;
    }
    function removeGranter(address oldGranter) onlyOwner public {
        granters[oldGranter] = false;
    }
}
