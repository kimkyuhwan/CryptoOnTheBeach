module.exports = {
	"secret": "The true sign of intelligence is not knowledge but imagination. Albert Einstein",
	"accessExpiresInDay": 7,
	"development_database": {
		"host": "localhost",
		"user": "root",
		"database": "blockchain_project_db"
	},
	"gasLimit": 300000,
	"secondhandMarket": {
		"address": "0x8a72da02adad9c4fa572171d6cd630382f760b41",
		"interface": [
		{
			"type": "function",
			"name": "registerProduct",
			"inputs": [{"name": "sn", "type": "bytes32"}],
			"outputs": []
		},
		{
			"type": "function",
			"name": "barter",
			"inputs": [
				{"name": "sn", "type": "bytes32"},
				{"name": "to", "type": "address"},
				{"name": "price", "type": "uint256"}
			]
		},
		]
	},
	"secondhandProduct": {
		"address": "0x2f57df7c57d6e0985b01fcbb71caa4f9d6b381ad",
		"interface": [
		{
			"type": "event",
			"name": "TransferProductOwnership",
			"inputs": [
				{"indexed": true, "name": "sn", "type": "bytes32"},
				{"indexed": true, "name": "from", "type": "address"},
				{"indexed": true, "name": "to", "type": "address"},
				{"indexed": false, "name": "price", "type": "uint256"}
			],
			"anonymous": false
		}
		]
	},
	"ducktoken": {
		"address": "0xae830f3f30a2c1d71959af31e6fdabbaafc2d3da",
		"interface": [
		{
			"type": "function",
			"name": "totalSupply",
			"inputs": [],
			"outputs": [{"name": "totalSupply", "type": "uint256"}]
		},
		{
			"type": "function",
			"name": "symbol",
			"inputs": [],
			"outputs": [{"name": "totalSupply", "type": "string"}]
		},
		{
			"type": "function",
			"name": "balanceOf",
			"inputs": [{"name": "address", "type": "address"}],
			"outputs": [{"name": "balance", "type": "uint256"}]
		},
		{
			"type": "function",
			"name": "transfer",
			"inputs": [
						{"name": "to", "type": "address"},
						{"name": "value", "type": "uint256"}
						],
			"outputs": [
						{"name": "success", "type": "uint256"}
						]
		}]
	}
};