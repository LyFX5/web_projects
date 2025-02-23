# Generated by Django 5.0 on 2024-02-16 17:37

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Post',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=20, verbose_name='Name')),
                ('autor_name', models.CharField(max_length=20, verbose_name='Autor Name')),
                ('to_post_page', models.URLField(verbose_name='Post Page Address')),
                ('posting_date', models.DateField(auto_now_add=True, verbose_name='Registration Date')),
            ],
        ),
    ]
