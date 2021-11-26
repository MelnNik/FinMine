from fastapi import FastAPI

from .what_works import main as ww_main
from .finmine import main as finmine

app = FastAPI()


@app.get("/works/{strategy}")
def works_strategy(strategy: str):
    return ww_main.what_works_strategy(strategy)


@app.get("/finmine/")
def fin_mine():
    return finmine.etfs_update()
