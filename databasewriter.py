
#Miksa mika.saari@tut.fi
#28.11.2016

import MySQLdb
#apt-get install python-mysqldb

#Testing local virtual LAMP-server
db = MySQLdb.connect("192.168.1.114", "testaaja2", "salasana", "javatesti2")
curs=db.cursor()
try:
    curs.execute ("""INSERT INTO USERS (NAME, PHONENUMBER, YEAR) VALUES ('Maija', '144234567', 2001)""")
    db.commit()
    print("Data committed")

except:
    print( "Error: the database is being rolled back")
    db.rollback()

