import finviz as fz

import pandas as pd


def scan_finviz(filters, table='Performance'):
    report = pd.DataFrame()
    try:

        stock_list = fz.screener.Screener(
            filters=filters, table=table)

        df = pd.DataFrame(stock_list.data)
        df.set_index('Ticker', inplace=True)
        df.drop(['No.'], axis=1, inplace=True)
        df.drop_duplicates(inplace=True)

        report = pd.concat([report, df])

    except Exception:
        pass

    report.drop_duplicates(inplace=True)

    return report
