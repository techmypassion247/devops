resource "aws_security_group" "dove_sg" {
  name        = "dove-sg"
  description = "Security group for Dove application"
  # vpc_id      = "vpc-0738906e3996d9fa1"  
  # Replace with your VPC ID

  # Ingress Rules (Inbound)
  ingress {
    description = "Allow SSH"
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Allow HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "Allow MySQL"
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Egress Rules (Outbound)
  egress {
    description = "Allow all outbound traffic IPv4"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "Allow all outbound traffic IPv6"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "dove-sg"
  }

  lifecycle {
    ignore_changes = all  # Only ignore tag changes, not entire resource
  }
}
