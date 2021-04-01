import logging
from logging import config


_LOGGING = {
    "version": 1,
    "disable_existing_loggers": False,
    "handlers": {
        "console": {"class": "logging.StreamHandler"},
    },
    "loggers": {
        "preprocess": {
            "handlers": ["console"],
            "level": logging.DEBUG,
        },
    },
}

config.dictConfig(_LOGGING)

logger = logging.getLogger("preprocess")

