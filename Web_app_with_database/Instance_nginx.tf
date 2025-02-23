resource "aws_instance" "nginx" {
  ami                         = data.aws_ami.amiID.id
  instance_type               = "t2.micro"
  key_name                    = "awskey"
  vpc_security_group_ids      = [aws_security_group.dove-sg.id]
  availability_zone           = "us-east-1a"
  subnet_id                   = "subnet-0310b7d86993a61af"
  associate_public_ip_address = true

  user_data = file("${path.module}/userdata_mysql.tpl")


  tags = {
    Name    = "aws-mysql"
    Project = "aws-server"
  }
}

resource "aws_ec2_instance_state" "web-state" {
  instance_id = aws_instance.nginx.id
  state       = "running"
}