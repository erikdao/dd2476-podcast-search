from peewee import *


db = PostgresqlDatabase(
    'podcast',
    user='postgres',
    password='postgres',
    host='localhost',
    port=5432)


class BaseModel(Model):
    class Meta:
        database = db


class Metadata(BaseModel):
    """Big model, direct representation on DB for the `metadata` tsv file"""

    id = IntegerField(primary_key=True)
    show_uri = CharField(max_length=50)
    show_name = CharField(max_length=255)
    show_description = TextField()
    publisher = CharField(max_length=255)
    language = CharField(max_length=50)
    rss_link = CharField(max_length=255)
    episode_uri = CharField(max_length=50)
    episode_name = CharField(max_length=255)
    episode_description = TextField()
    duration = DecimalField(15, 10)
    show_filename_prefix = CharField(max_length=50)
    episode_filename_prefix = CharField(max_length=255)

    class Meta:
        table_name = 'metadata'


class Show(BaseModel):
    """Model to represent a show"""

    show_uri = CharField(max_length=50, primary_key=True)
    show_name = CharField(max_length=255)
    show_description = TextField()
    publisher = CharField(max_length=255)
    language = CharField(max_length=50)
    rss_link = CharField(max_length=255)
    show_filename_prefix = CharField(max_length=50)

    class Meta:
        table_name = 'shows'

    def __str__(self):
        return self.show_uri


class Episode(BaseModel):
    """Model to represent an episode, which belongs to a show"""

    episode_uri = CharField(max_length=50, primary_key=True)
    episode_name = CharField(max_length=255)
    episode_description = TextField()
    duration = DecimalField(15, 10)
    episode_filename_prefix = CharField(max_length=255)

    show = ForeignKeyField(Show, field='show_uri', column_name='show_uri',
                           backref='episodes')

    class Meta:
        table_name = 'episodes'

    def __str__(self):
        return self.episode_uri
