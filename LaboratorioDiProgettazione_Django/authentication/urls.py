from django.contrib import admin
from django.urls import path, include


from . import views

# nel file urls.py di una app vengono definiti gli url offerti dal sito e quale funzione chiamare quando richiesti
urlpatterns = [
    path('', views.home, name="home"),
    path('signin/', views.signin, name="signin"),
    path('signout/', views.signout, name="signout"),
    path('home/', views.firstPage, name="firstPage"),
    path('preventivi/serve_pdf/<int:id_preventivo>/', views.serve_pdf, name='serve_pdf'),
]

