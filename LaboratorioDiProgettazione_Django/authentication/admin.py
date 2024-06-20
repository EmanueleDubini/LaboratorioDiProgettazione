from django.contrib import admin
from .models import Clienti


# Register your models here.
class ClientiAdmin(admin.ModelAdmin):
    list_display = ['nome', 'cognome', 'email', 'telefono']


admin.site.register(Clienti, ClientiAdmin)
