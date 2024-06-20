from django.conf import settings
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.models import User
from django.contrib import messages
from django.core.mail import send_mail
from django.http import HttpResponse
from django.shortcuts import render, redirect, get_object_or_404

from .models import Clienti, Garage, Preventivi


# Create your views here.
def home(request):
    clients = Clienti.objects.all()
    context = {
        'clients': clients
    }

    return render(request, "authentication/signin.html", context)


def signin(request):
    if request.method == 'POST':
        email = request.POST['email']
        pass1 = request.POST['pass1']

        # Utilizza il backend di autenticazione personalizzato
        user = authenticate(request, email=email, password=pass1)
        if user is not None:
            login(request, user, backend='authentication.backends.ClientiBackend')
            # Trova l'ID del cliente corrispondente a questo utente
            try:
                cliente = Clienti.objects.get(email=email)
                autos = Garage.objects.filter(
                    id_cliente=cliente.id_cliente)  # Filtra le auto basandosi sull'ID del cliente
                preventivi = Preventivi.objects.filter(id_cliente=cliente.id_cliente)
                return render(request, "authentication/garage_stato.html", {
                    "fname": user.nome,
                    "autos": autos,
                    "preventivi": preventivi,
                })
            except Clienti.DoesNotExist:
                messages.error(request, "Cliente non trovato.")
                return redirect('signin')
        else:
            messages.error(request, "Autenticazione fallita! Controlla l'email e la password.")
            return redirect('signin')

    return render(request, "authentication/signin.html")


def signout(request):
    logout(request)
    messages.success(request, "Logged Out Successfully!!")
    return redirect('home')


def firstPage(request):
    if not request.user.is_authenticated:
        return redirect('login')  # Assicurati che l'utente sia autenticato

    try:
        cliente = Clienti.objects.get(email=request.user.email)  # Trova il cliente associato all'utente
    except Clienti.DoesNotExist:
        messages.error(request, "Cliente non trovato.")
        return redirect('signin')

    autos = Garage.objects.filter(id_cliente=cliente.id_cliente)  # Filtra le auto basandosi sull'ID del cliente
    preventivi = Preventivi.objects.filter(id_cliente=cliente.id_cliente).order_by(
        '-data_creazione')  # Filtra i preventivi basandosi sull'ID del cliente

    if not autos:
        messages.info(request, "Nessuna auto registrata per questo utente.")

    # Seleziona il preventivo più recente o usa un ID specifico se necessario
    # Qui, assumiamo di prendere il più recente per semplicità
    preventivo_recente = preventivi.first() if preventivi.exists() else None

    return render(request, 'authentication/garage_stato.html', {
        'autos': autos,
        'fname': request.user.first_name,
        'preventivi': preventivi,
        'preventivo_recente': preventivo_recente,  # Passa il preventivo più recente
    })


def serve_pdf(request, id_preventivo):
    preventivo = get_object_or_404(Preventivi, pk=id_preventivo)
    if not preventivo.file_PDF:
        return HttpResponse("Nessun file PDF disponibile", status=404)

    response = HttpResponse(preventivo.file_PDF, content_type='application/pdf')
    response['Content-Disposition'] = f'attachment; filename="Preventivo_{id_preventivo}.pdf"'
    return response
