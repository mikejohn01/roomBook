# Roombook

Roombook is a software for booking meeting room.

## System requirements

- Java 8
- PostgreSQL
- Maven

## Using the application

To use the app, you must complete the following steps

### Create the Database

To create a new database, run the following commands at the postgres prompt:

   `$ sudo -u postgres psql`
   
   `postgres=# create database roombook;`
    
   `postgres=# create user postgres with encrypted password 'Progs2013';`
    
   `postgres=# grant all privileges on database meetroom to postgres;`

Then you need to restore a dump of the database (run command in meetroom directory):

   `$ sudo -u postgres psql -d roombook -f db/roombook_dump`

### Usage

Open link in a browser: http://localhost:8080/.
You need to log in (for example, username: 'admin', password: '123').
All events shown on the booking page.
For booking room - click 'Add New Meeting' button.
Fill in New Meeting form.
If you want to see a detail of meeting you need click on the event card.