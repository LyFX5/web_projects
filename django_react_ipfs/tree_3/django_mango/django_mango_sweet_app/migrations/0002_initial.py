
from django.db import migrations

def create_data(apps, schema_editor):
    Post = apps.get_model('django_mango_sweet_app', 'Post')
    Post(name="Joe Silver", autor_name="joe", to_post_page="https://blog.logrocket.com/using-react-django-create-app-tutorial/").save()


class Migration(migrations.Migration):

    dependencies = [
        ('django_mango_sweet_app', '0001_initial'),
    ]

    operations = [
        migrations.RunPython(create_data),
    ]
