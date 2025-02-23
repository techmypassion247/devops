resource "aws_security_group" "dove-sg" {
  name        = "dove-sg"
  description = "dove-sg"
  tags = {
    Name = "dove-sg"
  }

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}

resource "aws_vpc_security_group_ingress_rule" "sshfromyIP" {
  security_group_id = aws_security_group.dove-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 22
  ip_protocol       = "tcp"
  to_port           = 22

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}

resource "aws_vpc_security_group_ingress_rule" "allow_http" {
  security_group_id = aws_security_group.dove-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 80
  ip_protocol       = "tcp"
  to_port           = 80

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}

resource "aws_vpc_security_group_ingress_rule" "allow_mysql" {
  security_group_id = aws_security_group.dove-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  from_port         = 3306
  ip_protocol       = "tcp"
  to_port           = 3306

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}

resource "aws_vpc_security_group_egress_rule" "allowAllOutbound_ipv4" {
  security_group_id = aws_security_group.dove-sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1" # semantically equivalent to all ports

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}

resource "aws_vpc_security_group_egress_rule" "allowAllOutbound_ipv6" {
  security_group_id = aws_security_group.dove-sg.id
  cidr_ipv6         = "::/0"
  ip_protocol       = "-1" # semantically equivalent to all ports

  lifecycle {
    ignore_changes = all  # Prevent Terraform from modifying or recreating this resource
  }
}