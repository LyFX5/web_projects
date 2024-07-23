from django.shortcuts import render
from django.http import HttpResponse

# A viewset is a class-based view, able to handle all of the basic HTTP requests:
# GET, POST, PUT, DELETE without hard coding any of the logic.
# And if you have specific needs, you can overwrite those methods.

# Create your views here.

def index(request):
    return HttpResponse("Hello, world. You're at the ipdb_application_backend_django index.")