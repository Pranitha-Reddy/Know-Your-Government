package com.example.pranijareddy.knowyourgovernment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pranijareddy on 4/7/2017.
 */

public class parseData {
    int count;

    public ArrayList<Official> parse(String s) {

        ArrayList<Official> finalList = new ArrayList<>();
        ArrayList<String> off = new ArrayList<>();




        try {
            JSONObject jObjMain = new JSONObject(s);


            JSONArray offices = jObjMain.getJSONArray("offices");
            count = offices.length();
            for (int i = 0; i < offices.length(); i++) {
                JSONObject jOffice = (JSONObject) offices.get(i);
                String office = jOffice.getString("name");
                JSONArray index = jOffice.getJSONArray("officialIndices");
                for (int j = 0; j < index.length(); j++) {
                    off.add(office);
                }
            }
            JSONArray official = jObjMain.getJSONArray("officials");
            for (int i = 0; i < official.length(); i++) {
                JSONObject jofficial = (JSONObject) official.get(i);
                String url = "";
                String email = "";
                String phone = "";
                String party = "unknown";
                String photo = null;
                String add = "";
                String city = null;
                String state = null;
                String zip = null;
                String gid = "";
                String fid = "";
                String tid = "";
                String yid = "";
                String name = jofficial.getString("name");
                if (jofficial.has("address")) {
                    JSONArray address = jofficial.getJSONArray("address");
                    JSONObject jaddress = (JSONObject) address.get(0);
                    add = jaddress.getString("line1") ;
                    if (jaddress.has("line2")) {
                       add += ", "+jaddress.getString("line2") ;
                    }
                    if (jaddress.has("line3")) {
                        add += ", "+jaddress.getString("line3");
                    }
                    add += "\n "+jaddress.getString("city")+", ";
                    add += jaddress.getString("state")+" ";
                    add += jaddress.getString("zip");
                }
                if (jofficial.has("party")) {
                    party = jofficial.getString("party");
                }

                if (jofficial.has("phones")) {

                    JSONArray phones = jofficial.getJSONArray("phones");
                    phone = phones.getString(0);
                }

                if (jofficial.has("urls")) {

                    JSONArray urls = jofficial.getJSONArray("urls");
                    url = String.valueOf(urls.get(0));
                }

                if (jofficial.has("emails")) {

                    JSONArray emails = jofficial.getJSONArray("emails");
                    email = String.valueOf(emails.get(0));
                }
                if (jofficial.has("photoUrl")) {
                    photo = String.valueOf(jofficial.get("photoUrl"));
                }
                if (jofficial.has("channels")) {


                    String type = null;
                    JSONArray channel = jofficial.getJSONArray("channels");
                    for (int c = 0; c < channel.length(); c++) {
                        JSONObject jchannel = (JSONObject) channel.get(c);
                        type = jchannel.getString("type");
                        if (type.contentEquals("GooglePlus")) {
                            gid = jchannel.getString("id");
                        } else if (type.contentEquals("Facebook")) {
                            fid = jchannel.getString("id");
                        } else if (type.contentEquals("Twitter")) {
                            tid = jchannel.getString("id");
                        } else if (type.contentEquals("YouTube")) {
                            yid = jchannel.getString("id");
                        }
                    }

                }
                finalList.add(new Official(off.get(i), name, add, city, state, zip, party, phone, url, email, photo, gid, fid, tid, yid));
            }
            return finalList;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public String loc(String s) {

        String location = null;
        JSONObject jObjMain = null;
        try {
            jObjMain = new JSONObject(s);
            JSONObject jloc = jObjMain.getJSONObject("normalizedInput");
            location = jloc.get("city") + ", ";
            location += jloc.get("state") + " ";
            location += jloc.get("zip") + ".";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return location;
    }
}