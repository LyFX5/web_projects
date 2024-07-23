from django.apps import AppConfig


class DjangoMangoSweetAppConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'django_mango_sweet_app'


class UserConfig(AppConfig):
    name = 'django_mango_sweet_app.user'
    label = 'django_mango_sweet_app_user'
