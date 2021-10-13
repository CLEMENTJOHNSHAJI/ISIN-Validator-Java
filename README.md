# ISIN-Validator-Java
Java dynamic web service application for ISIN code validation.
Every security is uniquely identified by an International Securities Identification Number (ISIN), for example the iSharesCoreMSCIWorldUCITSETFUSD(Acc)withISINIE00B4L5Y983.TheGerman counterpart is the WKN (= Wertpapierkennnummer) which is a bit shorter: A0RPWH.

The structure of an ISIN is defined in ISO 6166 – for more information find the specification here: https://www.isin.org/isin-format/

Hint: Although it says that the ISIN must start with a two-letter country code, it is in fact of no consequence whether the two first letters are a country code. “EU”, “XA”, “XB”, “XC” and “XD” are also valid options (for more information why this is, see here: https://www.anna-web.org/wp- content/uploads/2018/05/ISIN-Guidelines-Version-14_clean.pdf, 2.2 Guidance relating to allocation rules).

Acceptancecriteria:

• if the service is given a valid ISIN, it should give corresponding feedback

• if the service is given an invalid ISIN, it should also say why it is invalid and what would have been
correct (if possible)

• if the service is given no input or input which does not qualify as an ISIN, it should give
corresponding feedback

• the implementation is tested
