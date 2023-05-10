import requests
import json


class BalasamajamHttpHelper:
    jwt_token = None

    host = "http://15.206.148.50:8080"
    login_url = f"{host}/login"
    add_member_url = f"{host}/addMember"
    add_init_amounts_url = f"{host}/addMasavari"
    find_member_url = f"{host}/fetchMembers"

    username = ""
    password = ""

    def __init__(self, username, password):
        self.username = username
        self.password = password

    @property
    def get_token(self):
        return self.jwt_token

    def _send_post(self, url, body, headers):
        return requests.post(url=url, json=body)

    def login(self):
        request_body = {
            "username": self.username,
            "password": self.password
        }

        response = self._send_post(self.login_url, request_body, {})
        result = response.json()
        self.jwt_token = result["data"]["token"]

    def add_member(self, fullname, local_name, address="", phone="", email="") -> bool:

        if self.jwt_token:
            request_body = {
                "token": self.jwt_token,
                "data": {
                    "fullName": fullname.strip(),
                    "localizedFullName": local_name.strip(),
                    "address": address.strip(),
                    "phone": phone.strip(),
                    "email": email.strip()
                }
            }

            response = self._send_post(self.add_member_url, request_body, {})
            result = response.json()
            status = result["status"]

            if status == "OK":
                return True
        else:
            print("JWT Token cannot be empty. Try logging in first!")

        return False

    def add_member_amounts(self, member_id, date, masavari_amount, maranavari_amount, paid_amount) -> bool:
        if self.jwt_token:
            # add masavari and maranavari
            request_body = {
                "token": self.jwt_token,
                "data": {
                    "memberId": member_id,
                    "date": date,
                    "maranavariAmount": float(masavari_amount),
                    "masavariAmount": float(maranavari_amount),
                    "paidAmount": float(paid_amount)
                }
            }

            response = self._send_post(self.add_init_amounts_url, request_body, {})
            result = response.json()
            print(result)
            status = result["status"]

            if status == "OK":
                return True
        else:
            print("JWT Token cannot be empty. Try logging in first!")

        return False

    def find_members(self, search_text):
        request_body = {
            "token": self.jwt_token,
            "data": {
                "searchText": search_text.strip()
            }
        }

        response = self._send_post(self.find_member_url, request_body, {})
        result = response.json()
        print(result)
        status = result["status"]

        if status == "OK":
            return result["data"]

        return []


if __name__ == "__main__":
    balasamjamHelper = BalasamajamHttpHelper("admin", "admin")
    balasamjamHelper.login()
    print(balasamjamHelper.get_token)
