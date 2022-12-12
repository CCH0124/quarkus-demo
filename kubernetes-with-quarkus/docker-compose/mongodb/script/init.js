rs.status();
use devices
db.createUser({user: 'test', pwd: 'test1234', roles: [ { role: 'readWrite', db: 'devices' } ]});

