pip install django

pip install mysqlclient

python manage.py makemigrations
python manage.py migrate

per autoimportare i modelli presenti nel database
python manage.py inspectdb > models.py


comando usato per aggiungere l'app "authentication" al progetto:
django-admin startapp authentication

se si vuole eseguire il server è possibile
entrare nella cartella "LaboratorioDiProgettazione_locale" con il comando cd LaboratorioDiProgettazione_locale
per poi eseguire il comando dal terminale: python manage.py runserver

per creare la sezione da amministratore per il sito usare il comado:
python .\manage.py createsuperuser
