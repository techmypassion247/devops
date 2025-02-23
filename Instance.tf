data "template_file" "userdata" {
  template = file("${path.module}/userdata.tpl")
}

resource "aws_instance" "web" {
  ami                         = data.aws_ami.amiID.id
  instance_type               = "t2.micro"
  key_name                    = "awskey"
  vpc_security_group_ids      = [aws_security_group.dove-sg.id]
  availability_zone           = "us-east-1a"
  subnet_id                   = "subnet-0310b7d86993a61af"
  associate_public_ip_address = true

  user_data = data.template_file.userdata.rendered


  tags = {
    Name    = "aws-nginx"
    Project = "aws-server"
  }
}

resource "aws_ec2_instance_state" "web-state" {
  instance_id = aws_instance.web.id
  state       = "running"
}