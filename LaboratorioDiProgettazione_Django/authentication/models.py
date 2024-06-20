from django.db import models


class Clienti(models.Model):
    id_cliente = models.AutoField(primary_key=True)
    nome = models.CharField(max_length=255, blank=True, null=True)
    cognome = models.CharField(max_length=255, blank=True, null=True)
    email = models.CharField(max_length=255, blank=True, null=True, unique=True)
    telefono = models.CharField(max_length=255, blank=True, null=True)
    password = models.CharField(max_length=255, blank=True, null=True)
    last_login = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'clienti'

    def __str__(self):
        return "{self.nome} {self.cognome} / {self.email}"


class Garage(models.Model):
    targa = models.AutoField(primary_key=True)
    telaio = models.CharField(max_length=255, blank=True, null=True)
    marca_vettura = models.CharField(max_length=255, blank=True, null=True)
    modello = models.CharField(max_length=255, blank=True, null=True, unique=True)
    id_cliente = models.IntegerField(blank=True, null=True)
    id_dipendente = models.IntegerField(blank=True, null=True)
    diagnosi = models.CharField(max_length=255, blank=True, null=True, unique=True)
    stato_riparazione = models.CharField(max_length=255, blank=True, null=True)
    id_preventivo = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'garage'


class Preventivi(models.Model):
    id_preventivo = models.AutoField(primary_key=True)
    id_cliente = models.IntegerField(blank=True, null=True)
    file_PDF = models.BinaryField()
    data_creazione = models.DateField()
    reparto_di_creazione = models.CharField(max_length=100)
    prezzo_totale = models.DecimalField(max_digits=10, decimal_places=2)

    class Meta:
        managed = False
        db_table = 'preventivi'
