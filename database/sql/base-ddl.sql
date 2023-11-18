CREATE TABLE customers (
	Username varchar(255),
	Password varchar(255),
	FirstName varchar(255),
	CardNum1 varchar(16),
	CardNum2 varchar(16),
	CardNum3 varchar(16),
	primary key(Username)
);
	
CREATE TABLE orders (
	FirstName varchar(255),
	BurgerCount varchar(255),
	SandwichCount varchar(255),
	ChickenCount varchar(255),
	ColaCount varchar(255),
	TotalMoney varchar(255),
	Reference varchar(255),
	primary key(Reference)
);
	