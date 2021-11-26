import numpy as np

import app.scanner.main as scan


def market_leaders():
    filters = ['cap_midover', 'fa_div_pos', 'fa_pfcf_u20', 'fa_roa_pos', 'fa_roe_pos', 'fa_roi_pos', 'fa_salesqoq_o10', 'sh_outstanding_o50', 'geo_usa']

    df = scan.scan_finviz(filters, 'Financial')
    df.replace('-', np.nan, inplace=True)
    df.dropna(subset=['Dividend'], inplace=True)
    df['Dividend'] = df['Dividend'].str.rstrip('%').astype(float)
    return df.sort_values(by='Dividend', ascending=False).head(25).index.values.tolist()


# Template for the strategies, categorized by the Yearly Performance
def performance_strategy(filters: list, number_of_tickers: int):
    df = scan.scan_finviz(filters)
    df.replace('-', np.nan, inplace=True)
    df.dropna(subset=['Perf Year'], inplace=True)
    df['Perf Year'] = df['Perf Year'].str.rstrip('%').astype(float)

    return df.sort_values(by='Perf Year', ascending=False).head(number_of_tickers).index.values.tolist()


def market_leaders_25():
    return performance_strategy(['cap_largeover', 'fa_ps_u4', 'fa_roe_pos', 'geo_usa', 'sh_outstanding_o50', 'ta_perf_13wup', ',ta_perf2_26wup'], 25)


def micro_growth_25():
    return performance_strategy(['cap_micro', 'fa_eps5years_o5', 'fa_epsyoy_pos', 'fa_opermargin_pos', 'fa_roi_pos', 'fa_sales5years_pos', 'geo_usa'], 25)


def improved_csg():
    return performance_strategy(['ta_perf_13w10o', 'ta_perf2_26w10o', 'fa_epsyoy_pos', 'fa_ps_u2', 'cap_smallover', 'geo_usa'], 25)


def price_change_top_25():
    return performance_strategy(['cap_micro', 'fa_ps_low', 'ta_perf_13wup', 'geo_usa', 'ta_perf2_26wup'], 25)


# Template for the strategies, categorized by the Free-Cash-Flow
def cash_flow_strategy(filters: list, number_of_tickers: int):
    df = scan.scan_finviz(filters, 'Valuation')
    df['P/FCF'].replace('-', np.nan, inplace=True)
    df.dropna(subset=['P/FCF'], inplace=True)
    df['P/FCF'] = df['P/FCF'].str.rstrip('%').astype(float)
    return df.sort_values(by='P/FCF', ascending=True).head(number_of_tickers).index.values.tolist()


def superior_value():
    return cash_flow_strategy(['fa_div_o2', 'fa_pb_u2', 'fa_pe_u15', 'geo_usa'], 50)


def small_50():
    return cash_flow_strategy(['cap_small', 'fa_roa_pos', 'fa_roe_pos', 'fa_roi_pos', 'geo_usa'], 50)


def mid_50():
    return cash_flow_strategy(['cap_mid', 'fa_div_o1', 'fa_pb_u2', 'fa_pe_u15', 'geo_usa'], 50)


def large_10():
    return cash_flow_strategy(
        ['cap_large', 'fa_div_pos', 'fa_pfcf_u20', 'fa_roa_pos', 'fa_roe_pos', 'fa_roi_pos', 'fa_salesqoq_o10', 'sh_outstanding_o50', 'geo_usa'], 10)


strategies = {'price_change_top_25': price_change_top_25, 'large_10': large_10, 'mid_50': mid_50, 'small_50': small_50, 'market_leaders_25': market_leaders_25,
              'micro_growth_25': micro_growth_25, 'superior_value': superior_value, 'market_leaders': market_leaders, 'improved_csg': improved_csg}


def what_works_strategy(strategy: str):
    return strategies[strategy]()
