pragma solidity ^0.4.16;

import "./granted.sol";

contract SecondhandProduct is Granted {
    mapping (bytes32 => address) ownerOf;
    
    event TransferProductOwnership(bytes32 indexed sn, address indexed from, address indexed to, uint256 price);
    
    function registerProductOwnership (bytes32 sn, address to) onlyGranters public {
        require(ownerOf[sn] == address(0x0));
        ownerOf[sn] = to;
        TransferProductOwnership(sn, address(0x0), to, 0x0);
    }
    function transferProductOwnership (bytes32 sn, address to, uint256 price) onlyGranters public {
        require(msg.sender == ownerOf[sn]);
        require(to != 0x0);
        require(price != 0x0);
        ownerOf[sn] = to;
        TransferProductOwnership(sn, msg.sender, to, price);
    }
}
