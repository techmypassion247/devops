resource "aws_key_pair" "awskey" {
  key_name   = "awskey"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDoeoNOImpjcR+SjdygD6im+Gm38MpE6YMQ+OZujfC/NCGT43JhzKKAEkT08ieBjf51cd+x1GabZKNw2kloHjaTzOeeQIXSbnIBGEqZ4q4chbWnvDtr4viXzp8Wkb3Lj5IfN/eO/p/PsvGPFI+mgZ+0wEpv9ODBoOkzL7kOIp1xqVNFC1M4FDGdMwdp9KOnjCY8Q7cJE7v0n79AmQJtRpqNgAN4gsosDgucs/G6aWpZOHu/8SHTQggJFfxOo33c0Y5Ejz6uTbnDm0TjCELJQEeBlj9A4U6XNgDsPKnLRDINWVQGJvCgJhjkM1H5dGdMhmr7JgyAVbDbde/M0QG2rE2slLWe2rYQPdagrS6d7mjMyHyI0LnCUf07Yce+0z66wMMEcmDqjMLWls11vVpc5yFC5CP2LcCXxcIYMWouUJAx7/cwGwVi2AnB760Tm82F0O9omiZXv6GnFr4HTemL7B/HW4KONK1PbcSmX9YTm3uFVagIkj6UVobB5asZoY1gfSU= TECH@DESKTOP-KF5T5PG"
  
  lifecycle {
    ignore_changes = all
  }
}