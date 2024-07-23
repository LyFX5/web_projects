from django.db import models

# Create your models here.

# class Student(models.Model):
#     name = models.CharField("Name", max_length=240)
#     email = models.EmailField()
#     document = models.CharField("Document", max_length=20)
#     phone = models.CharField(max_length=20)
#     registrationDate = models.DateField("Registration Date", auto_now_add=True)
#
#     def __str__(self):
#         return self.name

# class User(models.Model):
#     public_key = models.CharField("Name", max_length=20)
#     private_key = models.CharField("Password", max_length=10, blank=True)
#     registration_date = models.DateField("Registration Date", auto_now_add=True)
#     def __str__(self):
#         return self.public_key

class Post(models.Model):
    name = models.CharField("Name", max_length=20)
    autor_name = models.CharField("Autor Name", max_length=20)
    to_post_page = models.URLField("Post Page Address", max_length=200)
    posting_date = models.DateField("Registration Date", auto_now_add=True)
    def __str__(self):
        return self.name



