package com.tgid.tri.auth

import com.tgid.tri.data.ImportLog
import groovy.json.JsonSlurper

class CountryService {

    def importLoggingService

    void seedCountries() {
        def countries = new JsonSlurper().parseText(countryJson)
        countries.each {
            try {
                Country.findOrSaveWhere(countryID: it.CountryID.trim(), countryID3: it.CountryID3.trim(), countryName: it.CountryName.trim())
            } catch(Exception e) {
                importLoggingService.save(new ImportLog(importName: 'Seed Countries', error: true, description: e.message, complete: true))
                log.error e
            }
        }
    }

    private getCountryJson() {
        return """[{"CountryID":"AF","CountryID3":"AFG","CountryName":"Afghanistan"},{"CountryID":"AL","CountryID3":"ALB","CountryName":"Albania"},{"CountryID":"DZ","CountryID3":"DZA","CountryName":"Algeria"},{"CountryID":"AD","CountryID3":"AND","CountryName":"Andorra"},{"CountryID":"AO","CountryID3":"AGO","CountryName":"Angola"},{"CountryID":"AI","CountryID3":"AIA","CountryName":"Anguilla"},{"CountryID":"AQ","CountryID3":"ATA","CountryName":"Antarctica"},{"CountryID":"AG","CountryID3":"ATG","CountryName":"Antigua and Barbuda"},{"CountryID":"AR","CountryID3":"ARG","CountryName":"Argentina"},{"CountryID":"AW","CountryID3":"ABW","CountryName":"Aruba"},{"CountryID":"AU","CountryID3":"AUS","CountryName":"Australia"},{"CountryID":"AT","CountryID3":"AUT","CountryName":"Austria"},{"CountryID":"BS","CountryID3":"BHS","CountryName":"Bahamas"},{"CountryID":"BH","CountryID3":"BHR","CountryName":"Bahrain"},{"CountryID":"BD","CountryID3":"BGD","CountryName":"Bangladesh"},{"CountryID":"BB","CountryID3":"BRB","CountryName":"Barbados"},{"CountryID":"BY","CountryID3":"BLR","CountryName":"Belarus"},{"CountryID":"BE","CountryID3":"BEL","CountryName":"Belgium"},{"CountryID":"BM","CountryID3":"BMU","CountryName":"Bermuda"},{"CountryID":"BA","CountryID3":"BIH","CountryName":"Bosnia and Herzegovina"},{"CountryID":"BW","CountryID3":"BWA","CountryName":"Botswana"},{"CountryID":"BR","CountryID3":"BRA","CountryName":"Brazil"},{"CountryID":"BG","CountryID3":"BGR","CountryName":"Bulgaria"},{"CountryID":"BF","CountryID3":"BFA","CountryName":"Burkina Faso"},{"CountryID":"KH","CountryID3":"KHM","CountryName":"Cambodia"},{"CountryID":"CA","CountryID3":"CAN","CountryName":"Canada"},{"CountryID":"KY","CountryID3":"CYM","CountryName":"Cayman Islands"},{"CountryID":"CL","CountryID3":"CHL","CountryName":"Chile"},{"CountryID":"CN","CountryID3":"CHN","CountryName":"China"},{"CountryID":"CO","CountryID3":"COL","CountryName":"Colombia"},{"CountryID":"CR","CountryID3":"CRI","CountryName":"Costa Rica"},{"CountryID":"HR","CountryID3":"HRV","CountryName":"Croatia"},{"CountryID":"CU","CountryID3":"CUB","CountryName":"Cuba"},{"CountryID":"CY","CountryID3":"CYP","CountryName":"Cyprus"},{"CountryID":"CZ","CountryID3":"CZE","CountryName":"Czech Republic"},{"CountryID":"DK","CountryID3":"DEN","CountryName":"Denmark"},{"CountryID":"DO","CountryID3":"DOM","CountryName":"Dominican Republic"},{"CountryID":"EC","CountryID3":"ECU","CountryName":"Ecuador"},{"CountryID":"EG","CountryID3":"EGY","CountryName":"Egypt"},{"CountryID":"SV","CountryID3":"SLV","CountryName":"El Salvador"},{"CountryID":"ET","CountryID3":"ETH","CountryName":"Ethiopia"},{"CountryID":"FJ","CountryID3":"FJI","CountryName":"Fiji"},{"CountryID":"FI","CountryID3":"FIN","CountryName":"Finland"},{"CountryID":"FR","CountryID3":"FRA","CountryName":"France"},{"CountryID":"GE","CountryID3":"GEO","CountryName":"Georgia"},{"CountryID":"DE","CountryID3":"DEU","CountryName":"Germany"},{"CountryID":"GR","CountryID3":"GRC","CountryName":"Greece"},{"CountryID":"GL","CountryID3":"GRL","CountryName":"Greenland"},{"CountryID":"GT","CountryID3":"GTM","CountryName":"Guatemala"},{"CountryID":"HT","CountryID3":"HTI","CountryName":"Haiti"},{"CountryID":"HN","CountryID3":"HND","CountryName":"Honduras"},{"CountryID":"HK","CountryID3":"HKG","CountryName":"Hong Kong"},{"CountryID":"HU","CountryID3":"HUN","CountryName":"Hungary"},{"CountryID":"IS","CountryID3":"ISL","CountryName":"Iceland"},{"CountryID":"IN","CountryID3":"IND","CountryName":"India"},{"CountryID":"ID","CountryID3":"IDN","CountryName":"Indonesia"},{"CountryID":"IR","CountryID3":"IRN","CountryName":"Iran"},{"CountryID":"IQ","CountryID3":"IRQ","CountryName":"Iraq"},{"CountryID":"IE","CountryID3":"IRL","CountryName":"Ireland"},{"CountryID":"IM","CountryID3":"IMA","CountryName":"Isle of Man"},{"CountryID":"IL","CountryID3":"ISR","CountryName":"Israel"},{"CountryID":"IT","CountryID3":"ITA","CountryName":"Italy"},{"CountryID":"CI","CountryID3":"CIV","CountryName":"Ivory Coast (C�te d\u0027Ivoire)"},{"CountryID":"JM","CountryID3":"JAM","CountryName":"Jamaica"},{"CountryID":"JP","CountryID3":"JPN","CountryName":"Japan"},{"CountryID":"JO","CountryID3":"JOR","CountryName":"Jordan"},{"CountryID":"KE","CountryID3":"KEN","CountryName":"Kenya"},{"CountryID":"KW","CountryID3":"KWT","CountryName":"Kuwait"},{"CountryID":"LB","CountryID3":"LBN","CountryName":"Lebanon"},{"CountryID":"LT","CountryID3":"LTU","CountryName":"Lithuania"},{"CountryID":"LU","CountryID3":"LUX","CountryName":"Luxembourg"},{"CountryID":"MW","CountryID3":"MWI","CountryName":"Malawi"},{"CountryID":"MY","CountryID3":"MYS","CountryName":"Malaysia"},{"CountryID":"MX","CountryID3":"MEX","CountryName":"Mexico"},{"CountryID":"MC","CountryID3":"MCO","CountryName":"Monaco"},{"CountryID":"MA","CountryID3":"MAR","CountryName":"Morocco"},{"CountryID":"XX","CountryID3":"---","CountryName":"N/A"},{"CountryID":"--","CountryID3":"---","CountryName":"n/a"},{"CountryID":"NL","CountryID3":"NED","CountryName":"Netherlands"},{"CountryID":"NZ","CountryID3":"NZL","CountryName":"New Zealand"},{"CountryID":"NG","CountryID3":"NGA","CountryName":"Nigeria"},{"CountryID":"NO","CountryID3":"NOR","CountryName":"Norway"},{"CountryID":"OM","CountryID3":"OMN","CountryName":"Oman"},{"CountryID":"PK","CountryID3":"PAK","CountryName":"Pakistan"},{"CountryID":"PA","CountryID3":"PAN","CountryName":"Panama"},{"CountryID":"PY","CountryID3":"PRY","CountryName":"Paraguay"},{"CountryID":"PE","CountryID3":"PER","CountryName":"Peru"},{"CountryID":"PH","CountryID3":"PHL","CountryName":"Philippines"},{"CountryID":"PL","CountryID3":"POL","CountryName":"Poland"},{"CountryID":"PT","CountryID3":"PRT","CountryName":"Portugal"},{"CountryID":"QA","CountryID3":"QAT","CountryName":"Qatar"},{"CountryID":"RO","CountryID3":"ROU","CountryName":"Romania"},{"CountryID":"RU","CountryID3":"RUS","CountryName":"Russia"},{"CountryID":"WS","CountryID3":"WSM","CountryName":"Samoa"},{"CountryID":"SN","CountryID3":"SEN","CountryName":"Senegal"},{"CountryID":"CS","CountryID3":"SCG","CountryName":"Serbia and Montenegro"},{"CountryID":"SG","CountryID3":"SGP","CountryName":"Singapore"},{"CountryID":"SK","CountryID3":"SVK","CountryName":"Slovakia"},{"CountryID":"SI","CountryID3":"SVN","CountryName":"Slovenia"},{"CountryID":"ZA","CountryID3":"ZAF","CountryName":"South Africa"},{"CountryID":"KR","CountryID3":"KOR","CountryName":"South Korea"},{"CountryID":"ES","CountryID3":"ESP","CountryName":"Spain"},{"CountryID":"LK","CountryID3":"LKA","CountryName":"Sri Lanka"},{"CountryID":"SE","CountryID3":"SWE","CountryName":"Sweden"},{"CountryID":"SU","CountryID3":"SUI","CountryName":"Switzerland"},{"CountryID":"SY","CountryID3":"SYR","CountryName":"Syria"},{"CountryID":"TW","CountryID3":"TWN","CountryName":"Taiwan"},{"CountryID":"TZ","CountryID3":"TZA","CountryName":"Tanzania"},{"CountryID":"TH","CountryID3":"THA","CountryName":"Thailand"},{"CountryID":"TG","CountryID3":"TGO","CountryName":"Togo"},{"CountryID":"TR","CountryID3":"TUR","CountryName":"Turkey"},{"CountryID":"UA","CountryID3":"UKR","CountryName":"Ukraine"},{"CountryID":"AE","CountryID3":"ARE","CountryName":"United Arab Emirates"},{"CountryID":"GB","CountryID3":"GBR","CountryName":"United Kingdom"},{"CountryID":"US","CountryID3":"USA","CountryName":"United States"},{"CountryID":"UM","CountryID3":"UMI","CountryName":"United States Minor Out Isl"},{"CountryID":"UY","CountryID3":"URY","CountryName":"Uruguay"},{"CountryID":"VI","CountryID3":"VIR","CountryName":"US Virgin Islands"},{"CountryID":"UZ","CountryID3":"UZB","CountryName":"Uzbekistan"},{"CountryID":"VE","CountryID3":"VEN","CountryName":"Venezuela"},{"CountryID":"VN","CountryID3":"VNM","CountryName":"Vietnam"},{"CountryID":"ZW","CountryID3":"ZWE","CountryName":"Zimbabwe"}]"""
    }
}
