from django.contrib.auth.backends import BaseBackend
from .models import Clienti


class ClientiBackend(BaseBackend):
    def authenticate(self, request, email=None, password=None):
        try:
            user = Clienti.objects.get(email=email, password=password)
            return user
        except Clienti.DoesNotExist:
            return None

    def get_user(self, user_id):
        try:
            return Clienti.objects.get(pk=user_id)
        except Clienti.DoesNotExist:
            return None
