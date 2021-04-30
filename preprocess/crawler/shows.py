"""
Show crawler
"""
import time
import random
import typing
import requests
from peewee import *

OAUTH_TOKEN = "BQCqdwpPrNXF-3W5oDw8y2sR-jXWb01Br3pmO7JxRRhkpmHt4p00Loj6YVgfUlI3sxni7yTYyNtUcKDVXlDCM08cMAp3oTSdrV3j92kePSp01VCrfkwUH9Ru7U4SqLavoeRQMX7469QAXND7cF3KEQgXOKk95SWxI7EvMIQrwud81R2DsqetDKP4sQf1"

DB = {
    'NAME': 'podcast',
    'USER': 'postgres',
    'PASSWORD': 'postgres'
}
db = PostgresqlDatabase(DB['NAME'], user=DB['USER'], password=DB['PASSWORD'],
                        host='localhost', port=5432)


def get_show(show_id: str) -> typing.Any:
    headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': f'Bearer {OAUTH_TOKEN}'
    }
    url = f'https://api.spotify.com/v1/shows/{show_id}'
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        print(f"Warning: Response status code {response.status_code}")

    return response.json()

def get_image_url(data: typing.Any) -> typing.Optional[str]:
    images = [im for im in data.get('images', []) if im.get('height', 0) == 640]
    if len(images) == 0:
        return None

    return images[0].get('url')
    

def update_image_db(show_id: str, image_url: str) -> None:
    if not image_url:
        return
    
    query = f"UPDATE shows SET show_image_url = '{image_url}' WHERE id = '{show_id}';"

    db.execute_sql(query)


def get_shows_to_update() -> typing.Any:
    query = "SELECT id FROM shows WHERE show_image_url IS NULL ORDER BY id ASC;"
    cursor = db.execute_sql(query)

    return cursor.fetchall()


def main():
    shows_cursor = get_shows_to_update()

    for cursor in shows_cursor:
        show = cursor[0]
        data = get_show(show)
        image_url = get_image_url(data)
        print(show, image_url)
        update_image_db(show, image_url)
        sleep_time = random.randint(5, 10)
        print(f"Going to sleep for {sleep_time} secs")
        time.sleep(sleep_time)


if __name__ == '__main__':
    main()
