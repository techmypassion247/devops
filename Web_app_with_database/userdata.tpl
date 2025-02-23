#cloud-config
runcmd:
  - sudo apt update -y
  - sudo apt install nginx -y
  - sudo systemctl start nginx
  - sudo systemctl enable nginx
  - sudo apt update
  - sudo apt install php -y
  - sudo apt install php-fpm -y
  - sudo apt install php-mysql -y
  - sudo apt Install mysql-client-core-8.0 -y 
  - sudo apt install unzip -y
  - sudo apt install git -y
  - git clone https://github.com/techmypassion247/devops.git
  - unzip devops/1802_php.zip -d prj01
  - sudo rm -f /etc/nginx/sites-available/default
  - sudo cp -r devops/default /etc/nginx/sites-available/
  - sudo cp -r prj01/1802_php/1802_php/* /var/www/html/
  - sudo systemctl restart nginx
  - sudo systemctl restart php8.1-fpm 

  # setup mysql database
  - sudo apt update -y
  - sudo apt install mysql-server -y
  - sudo systemctl start mysql
  - sudo systemctl enable mysql

  # Configure MySQL securely
  - sudo mysql -e "CREATE USER 'admin'@'%' IDENTIFIED WITH mysql_native_password BY 'Awsdb@2025';"
  - sudo mysql -e "GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;"
  - sudo mysql -e "CREATE DATABASE loginsystem;"
  - sudo mysql -e "FLUSH PRIVILEGES;"

  # Allow remote connections
  - sudo sed -i 's/^bind-address\s*=.*/bind-address = 0.0.0.0/' /etc/mysql/mysql.conf.d/mysqld.cnf

  # Restart MySQL to apply changes
  - sudo systemctl restart mysql

  # Clone database repository
  - git clone https://github.com/techmypassion247/database.git

  # Import database
  - sudo mysql -u admin -pAwsdb@2025 loginsystem < database/loginsystem.sql

  # Allow MySQL through firewall
  - sudo ufw allow 3306/tcp
  - sudo ufw reload