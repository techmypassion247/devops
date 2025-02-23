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
  - sudo cp -r prj01/1802_php/* /var/www/html/
  - sudo systemctl restart nginx
  - sudo systemctl restart php8.1-fpm