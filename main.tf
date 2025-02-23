terraform {
  backend "s3" {
    bucket = "terraform-state-myproject"
    key    = "terraform.tfstate"
    region = "us-east-1"
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "web" {
  ami           = "ami-04b4f1a9cf54c11d0"
  instance_type = "t2.micro"

  tags = {
    Name = "Terraform-Server"
  }
}

output "instance_public_ip" {
  value = aws_instance.web.public_ip
}
