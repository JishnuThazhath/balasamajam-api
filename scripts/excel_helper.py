import json
import traceback

import pandas as pd
from api_util import BalasamajamHttpHelper

class ExcelHelper:
    file = "MaranasamithiApplication.xlsm"

    def __init__(self, file=None):
        if file:
            self.file = file
        # login first to save the jwt_token for authentication
        self.bh = BalasamajamHttpHelper(username="admin", password="admin")
        self.bh.login()

    def add_members(self):
        df = pd.read_excel(self.file, sheet_name="Names")

        for idx in df.index:
            status = self.bh.add_member(fullname=df['English'][idx], local_name=df['Malayalam name'][idx])
            print(f"{df['English'][idx]} : {status}")

    def add_init_values(self):
        df_names = pd.read_excel(self.file, sheet_name="Names")
        df_transactions = pd.read_excel(self.file, sheet_name="Transactions")

        name = ""
        for idx in df_names.index:
            try:
                name = df_names['Malayalam name'][idx]
                all_rows = df_transactions.loc[df_transactions['Name'] == name]
                # filter the transactions list with name.
                # get the last row, which is the latest transaction.
                last_record = all_rows.iloc[-1]

                # get the member id
                members_list = self.bh.find_members(last_record["Name"])
                # json_data = json.loads(members_list[0])
                print(members_list[0]["memberId"])

                formatted_date = str(last_record["Date"]).replace("-", "/")
                print(formatted_date)
                # update masavari and maranavari
                status = self.bh.add_member_amounts(member_id=members_list[0]["memberId"],
                                                    masavari_amount=last_record["Vari balance"],
                                                    maranavari_amount=last_record["Maranam Balance"],
                                                    date=formatted_date, paid_amount=last_record["Paid Amount"])
                print(status)
            except Exception as e:
                print(f"Failed for user {name}. {e}")
                traceback.print_exc()


if __name__ == "__main__":
    eh = ExcelHelper()
    eh.add_members()
    eh.add_init_values()
