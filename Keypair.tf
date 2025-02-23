resource "aws_key_pair" "dove-key" {
  key_name   = "dove-key"
  public_key = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIKeG5SHj/MsCMzAVmPB9CFjU2WesXQRb3hDACsFZTI+o Imran Teli@Worker"
}

resource "aws_key_pair" "test-key" {
  key_name   = "test-key"
  public_key = "ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIFX8ty2kQwB0XV+a7ew2GII+gUPkHdW6a4sQK7etWsXs Imran Teli@Worker"
}

resource "aws_key_pair" "terrakey" {
  key_name   = "terrakey"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDB9aAoKD8Yj7q+88DgZ8wYtwCgmrHOoF4nLyVtnMkStAuo8DssNZsj9tni1QILou3wRX6Yl6mMzXuUuTa//NvEArcuK24YsZGn1nbJZKsCfPNxr6q1DaToBNpIwO2hw6LQRGqJG19deQRHGdrq+CRjOSi5fn+G8svjrgo4c87179wV8+wGX/rrzQdTvQtD0Pui+FJc8mSGUHwC7gLYxoqwZMupPnwJeBBOZY017YQvN7dme4w+2Jv3eZE5hoJ/xx17Xz8OgyyXWwkq8VQcBVIWk5RtObZXgf9Zzg/t/gAYGGaFM76HpyiZ515MqQUsXdaoAkg6RLj744+ARRePqXs31c/EaJTN+rVLX41FRB1adnM0SN5HWIk9Zm/eNZUeZeF5uQHtV/0GaMTtz0VrBnix1kTa9JkbOoRBxi18s800XxteeeMKgIcI4l4xDrLifZZgj8hht2yITM9TAXLF8xlSXYNvbbFv2x/ykqf68QdK59XZK29s35rQIN8Grcb4/d8= TECH@DESKTOP-KF5T5PG"
}

resource "aws_key_pair" "awskey" {
  key_name   = "awskey"
  public_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQDoeoNOImpjcR+SjdygD6im+Gm38MpE6YMQ+OZujfC/NCGT43JhzKKAEkT08ieBjf51cd+x1GabZKNw2kloHjaTzOeeQIXSbnIBGEqZ4q4chbWnvDtr4viXzp8Wkb3Lj5IfN/eO/p/PsvGPFI+mgZ+0wEpv9ODBoOkzL7kOIp1xqVNFC1M4FDGdMwdp9KOnjCY8Q7cJE7v0n79AmQJtRpqNgAN4gsosDgucs/G6aWpZOHu/8SHTQggJFfxOo33c0Y5Ejz6uTbnDm0TjCELJQEeBlj9A4U6XNgDsPKnLRDINWVQGJvCgJhjkM1H5dGdMhmr7JgyAVbDbde/M0QG2rE2slLWe2rYQPdagrS6d7mjMyHyI0LnCUf07Yce+0z66wMMEcmDqjMLWls11vVpc5yFC5CP2LcCXxcIYMWouUJAx7/cwGwVi2AnB760Tm82F0O9omiZXv6GnFr4HTemL7B/HW4KONK1PbcSmX9YTm3uFVagIkj6UVobB5asZoY1gfSU= TECH@DESKTOP-KF5T5PG"
}