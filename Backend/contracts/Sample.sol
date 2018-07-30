pragma solidity ^0.4.17;
contract SimpleStorage {
	uint val;
	constructor() public {
		val=123;
	}
	function set(uint x) public returns (uint){
		val=x;
		return val;
	}
	function get() public view returns (uint) {
		return val;
	}
}