import json

from datetime import timedelta
from datetime import date

from finviz.screener import Screener
import yfinance as yf

from pandas_datareader import data as pdr
import pandas as pd

pd.options.mode.chained_assignment = None
yf.pdr_override()

# Dictionary for finviz industries
industry_dict = {
    'Oil & Gas E&P': 'ind_oilgasep',
    'Oil & Gas Drilling': 'ind_oilgasdrilling',
    'Oil & Gas Refining & Marketing': 'ind_oilgasrefiningmarketing',
    'Oil & Gas Equipment & Services': 'ind_oilgasequipmentservices',
    'Oil & Gas Integrated': 'ind_oilgasintegrated',
    'Oil & Gas Midstream': 'ind_oilgasmidstream',
    'Utilities - Regulated Gas': 'ind_utilitiesregulatedgas',
    'Utilities - Regulated Water': 'ind_utilitiesregulatedwater',
    'Utilities - Regulated Electric': 'ind_utilitiesregulatedelectric',
    'Utilities - Independent Power Producers': 'ind_utilitiesindependentpowerproducers',
    'Paper & Paper Products': 'ind_paperpaperproducts',
    'Gold': 'ind_gold',
    'Scientific & Technical Instruments': 'ind_scientifictechnicalinstruments',
    'Apparel Manufacturing': 'ind_apparelmanufacturing',
    'Drug Manufacturers - Specialty & Generic': 'ind_drugmanufacturersspecialtygeneric',
    'Household & Personal Products': 'ind_householdpersonalproducts',
    'Software - Application': 'ind_softwareapplication',
    'Education & Training Services': 'ind_educationtrainingservices',
    'Chemicals': 'ind_chemicals',
    'Staffing & Employment Services': 'ind_staffingemploymentservices',
    'Packaging & Containers': 'ind_packagingcontainers',
    'Asset Management': 'ind_assetmanagement',
    'Diagnostics & Research': 'ind_diagnosticsresearch',
    'Communication Equipment': 'ind_communicationequipment',
    'Pollution & Treatment Controls': 'ind_pollutiontreatmentcontrols',
    'Electronic Components': 'ind_electroniccomponents',
    'Apparel Retail': 'ind_apparelretail',
    'Lumber & Wood Production': 'ind_lumberwoodproduction',
    'Medical Devices': 'ind_medicaldevices',
    'Drug Manufacturers - General': 'ind_drugmanufacturersgeneral',
    'Biotechnology': 'ind_biotechnology',
    'Consulting Services': 'ind_consultingservices',
    'Beverages - Non-Alcoholic': 'ind_beveragesnonalcoholic',
    'Farm & Heavy Construction Machinery': 'ind_farmheavyconstructionmachinery',
    'Computer Hardware': 'ind_computerhardware',
    'Internet Content & Information': 'ind_internetcontentinformation',
    'Specialty Business Services': 'ind_specialtybusinessservices',
    'Software - Infrastructure': 'ind_softwareinfrastructure',
    'Semiconductor Equipment & Materials': 'ind_semiconductorequipmentmaterials',
    'Electronic Gaming & Multimedia': 'ind_electronicgamingmultimedia',
    'Security & Protection Services': 'ind_securityprotectionservices',
    'Personal Services': 'ind_personalservices',
    'Internet Retail': 'ind_internetretail',
    'Financial Data & Stock Exchanges': 'ind_financialdatastockexchanges',
    'Medical Instruments & Supplies': 'ind_medicalinstrumentssupplies',
    'Health Information Services': 'ind_healthinformationservices',
    'Credit Services': 'ind_creditservices',
    'Information Technology Services': 'ind_informationtechnologyservices',
    'Beverages - Brewers': 'ind_beveragesbrewers',
    'Engineering & Construction': 'ind_engineeringconstruction',
    'Capital Markets': 'ind_capitalmarkets',
    'Insurance - Diversified': 'ind_insurancediversified',
    'Specialty Industrial Machinery': 'ind_specialtyindustrialmachinery',
    'Medical Distribution': 'ind_medicaldistribution',
    'Building Products & Equipment': 'ind_buildingproductsequipment',
    'Marine Shipping': 'ind_marineshipping',
    'Utilities - Diversified': 'ind_utilitiesdiversified',
    'Grocery Stores': 'ind_grocerystores',
    'Trucking': 'ind_trucking',
    'Medical Care Facilities': 'ind_medicalcarefacilities',
    'Auto Parts': 'ind_autoparts',
    'Furnishings, Fixtures & Appliances': 'ind_furnishingsfixturesappliances',
    'Residential Construction': 'ind_residentialconstruction',
    'Industrial Distribution': 'ind_industrialdistribution',
    'Insurance Brokers': 'ind_insurancebrokers',
    'Packaged Foods': 'ind_packagedfoods',
    'Airports & Air Services': 'ind_airportsairservices',
    'Semiconductors': 'ind_semiconductors',
    'Publishing': 'ind_publishing',
    'Electronics & Computer Distribution': 'ind_electronicscomputerdistribution',
    'Real Estate Services': 'ind_realestateservices',
    'Auto & Truck Dealerships': 'ind_autotruckdealerships',
    'Recreational Vehicles': 'ind_recreationalvehicles',
    'Building Materials': 'ind_buildingmaterials',
    'Electrical Equipment & Parts': 'ind_electricalequipmentparts',
    'Specialty Retail': 'ind_specialtyretail',
    'Insurance - Life': 'ind_insurancelife',
    'Food Distribution': 'ind_fooddistribution',
    'Metal Fabrication': 'ind_metalfabrication',
    'Business Equipment & Supplies': 'ind_businessequipmentsupplies',
    'Integrated Freight & Logistics': 'ind_integratedfreightlogistics',
    'Tools & Accessories': 'ind_toolsaccessories',
    'Discount Stores': 'ind_discountstores',
    'Leisure': 'ind_leisure',
    'Aerospace & Defense': 'ind_aerospacedefense',
    'REIT - Office': 'ind_reitoffice',
    'Footwear & Accessories': 'ind_footwearaccessories',
    'Home Improvement Retail': 'ind_homeimprovementretail',
    'Railroads': 'ind_railroads',
    'Telecom Services': 'ind_telecomservices',
    'Entertainment': 'ind_entertainment',
    'Broadcasting': 'ind_broadcasting',
    'Steel': 'ind_steel',
    'Other Industrial Metals & Mining': 'ind_otherindustrialmetalsmining',
    'Banks - Regional': 'ind_banksregional',
    'Insurance - Specialty': 'ind_insurancespecialty',
    'Mortgage Finance': 'ind_mortgagefinance',
    'REIT - Specialty': 'ind_reitspecialty',
    'Rental & Leasing Services': 'ind_rentalleasingservices',
    'Healthcare Plans': 'ind_healthcareplans',
    'Farm Products': 'ind_farmproducts',
    'Banks - Diversified': 'ind_banksdiversified',
    'Insurance - Property & Casualty': 'ind_insurancepropertycasualty'
}


def etfs_update():
    filters_long = ['cap_smallover', 'fa_curratio_o0.5', 'fa_debteq_u1', 'fa_eps5years_pos', 'fa_epsyoy_o10', 'fa_estltgrowth_pos', 'fa_pe_u35', 'fa_ps_u5',
                    'fa_roe_o10', 'fa_pb_u2']
    stock_list_long = Screener(filters=filters_long, table='Overview', order='price')
    stocks_long = pd.DataFrame(stock_list_long.data)
    stocks_long.set_index('Ticker', inplace=True)
    filters_growth = ['fa_debteq_u1', 'fa_eps5years_pos', 'fa_estltgrowth_pos', 'fa_pe_profitable', 'fa_ps_u5', 'fa_roe_o10', 'sh_avgvol_o300', 'sh_price_o10',
                      'ta_rsi_nos40', 'fa_pfcf_u20', 'fa_div_o1', 'fa_pb_u3']
    stock_list_growth = Screener(filters=filters_growth, table='Overview', order='price')
    stocks_growth = pd.DataFrame(stock_list_growth.data)
    stocks_growth.set_index('Ticker', inplace=True)
    stocks = pd.concat([stocks_long, stocks_growth])
    stocks.drop_duplicates(inplace=True)
    stocks.drop(['No.', 'Company', 'Country', 'Change', 'Sector', 'Volume'], inplace=True, axis=1)
    stocks = stocks.head(18)

    competitors = []
    for inde, row in stocks.iterrows():
        try:
            ind = industry_dict[stocks['Industry'].loc[inde]]

            filters_competitor = [ind, 'cap_smallover', 'fa_pe_o5', 'sh_price_o80', 'ta_rsi_nos40']
            competitors_list = []
            try:
                stock_list_competitor = Screener(filters=filters_competitor, table='Overview', order='price')
                for stock in stock_list_competitor:
                    competitors_list.append(stock['Ticker'])
            except Exception:
                pass
            if not competitors_list:
                competitors.append('-')
            else:
                competitors.append(competitors_list)
        except KeyError:
            pass

    stocks['Competitors'] = competitors

    stocks_to_analyze = stocks[(stocks['Competitors'] != '-')]
    stocks_to_analyze["Price"] = pd.to_numeric(stocks_to_analyze["Price"])
    results = pd.DataFrame(columns=['Start', 'End', 'Difference'])
    results_row = pd.DataFrame(columns=['Avg'])
    for row in stocks_to_analyze.index:
        for competitor in stocks_to_analyze.Competitors[row]:
            try:
                data = pdr.get_data_yahoo(competitor, start=date.today() - timedelta(days=365 * 3), end=date.today(), threads=True, progress=False)
                temp = pd.DataFrame(
                    {
                        'Start': data.Open.iloc[0],
                        'End': data.Open.iloc[-1],
                        'Difference': data.Open.iloc[-1] / data.Open.iloc[0]
                    }, index=[competitor]
                )
                results = pd.concat([results, temp])
            except Exception:
                pass
        temp_row = pd.DataFrame(
            {
                'Avg': results.Difference.mean()
            }, index=[row]
        )
        results_row = pd.concat([results_row, temp_row])
    stocks_to_analyze.drop('Competitors', axis=1, inplace=True)
    stocks_to_analyze['competitors_performance'] = results_row
    results_ticker = pd.DataFrame(columns=['Difference'])
    for ticker in stocks_to_analyze.index:
        try:
            data = pdr.get_data_yahoo(ticker, start=date.today() - timedelta(days=365 * 3), end=date.today(), threads=True, progress=False)
            temp = pd.DataFrame(
                {
                    'Difference': data.Open.iloc[-1] / data.Open.iloc[0]
                }, index=[ticker]
            )
            results_ticker = pd.concat([results_ticker, temp])
        except Exception:
            pass
    stocks_to_analyze['companys_increase'] = results_ticker
    stocks_to_analyze['difference_with_industry'] = stocks_to_analyze['competitors_performance'] - stocks_to_analyze['companys_increase']

    stocks_to_analyze['difference_multiplier'] = ''
    difference = stocks_to_analyze['difference_with_industry'].mean()
    for ticker in stocks_to_analyze.index:
        if (stocks_to_analyze['difference_with_industry'].loc[ticker] > difference).all():
            stocks_to_analyze['difference_multiplier'][ticker] = 2
        elif ((stocks_to_analyze['difference_with_industry'].loc[ticker] < difference) & (stocks_to_analyze['difference_with_industry'].loc[ticker] > 0)).all():
            stocks_to_analyze['difference_multiplier'][ticker] = 1
        else:
            stocks_to_analyze['difference_multiplier'][ticker] = 0.5

    stocks_to_analyze['total_multiplier'] = stocks_to_analyze['difference_multiplier']
    stocks_to_analyze.drop('difference_multiplier', axis=1, inplace=True)
    stocks_to_analyze.reset_index(inplace=True)

    result = json.loads(stocks_to_analyze[['Ticker', 'Price', 'total_multiplier']].to_json(orient='records', force_ascii=False))
    return result
