#cloud-config
runcmd:
  - sudo apt update -y
  - sudo apt install nginx -y
  - sudo systemctl start nginx
  - sudo systemctl enable nginx
  - sudo apt update
  - sudo apt install unzip -y
  - wget -o https://www.tooplate.com/download/2110_character
  - sudo unzip 2110_character -d projects
  - sudo cp -r projects/2110_character/* /var/www/html/
  - sudo systemctl restart nginx
