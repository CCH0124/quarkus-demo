db.createUser(
	{
		user: 'vehicle', 
		pwd: 'test1234', 
		roles: [ 
			{ 
				role: 'readWrite', 
				db: 'vehicle' 
			} 
		]
	}
);

