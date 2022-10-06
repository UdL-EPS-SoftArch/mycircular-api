# MyCircular-API

Backend for a circular economy Web application using Spring REST, HATEOAS, JPA, etc. Additional details: [HELP.md](HELP.md)

[![Open Issues](https://img.shields.io/github/issues-raw/UdL-EPS-SoftArch/mycircular-api?logo=github)](https://github.com/orgs/UdL-EPS-SoftArch/projects/15)
[![CI/CD](https://github.com/UdL-EPS-SoftArch/mycircular-api/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/UdL-EPS-SoftArch/mycircular-api/actions)
[![CucumberReports: UdL-EPS-SoftArch](https://messages.cucumber.io/api/report-collections/faed8ca5-e474-4a1a-a72a-b8e2a2cd69f0/badge)](https://reports.cucumber.io/report-collections/faed8ca5-e474-4a1a-a72a-b8e2a2cd69f0)
[![Deployment status](https://img.shields.io/uptimerobot/status/m792713336-92bf9993ec46d798b1dd89c0)](https://mycircular-api.fly.dev/users)

## Vision

**For** citizens **who** want to contribute to sustainability through a circular economy
**the project** MyCircular **is an** market of products of services
**that** allows to offer and request second hand products and repair products in exchange for platform tokens
**Unlike** other ...

## Features per Stakeholder

| **ANONYMOUS**           | **USER**     | **SELLER**            | **BUYER**              | **ADMIN**          |
|-------------------------|--------------|-----------------------|------------------------|--------------------|
| Register                | Login        | Offer Product         | Request Product        | Delete Any Offer   |
| List Product Requests   | Logout       | Edit Product Offer    | Edit Product Request   | Delete Any Request |
| List Product Offers     | Profile      | Delete Product Offer  | Delete Product Request |                    |
| Search Product Requests | List Ranked  | Sell Product          | Buy Product            |                    |
| Search Product Offers   | Review User  | Counter Offer Product | Counter Offer Product  |                    |
|                         | Message User | Exchange Products     |                        |                    |
|                         |              | Offer Service         | Request Service        |                    |
|                         |              | Edit Service Offer    | Edit Service Request   |                    |
|                         |              | Delete Service Offer  | Delete Service Request |                    |
|                         |              | Sell Service          | Buy Service            |                    |
|                         |              | Counter Offer Service | Counter Offer Service  |                    |
|                         |              | Confirm Sale?         | Confirm Purchase?      |                    |

## Entities Model

![EntityModelsDiagram](http://www.plantuml.com/plantuml/svg/5Sqn3a8X303GtLFe0Q2xqtVucoOcFnu0IvKcK4XfOxoz_kcDRvcMrBpLmua5gsTohSTYDkOPGDCdljcAFtsIOXSZiOYzmIDVmthVmjPVTb4iqBx8YbwJXZGOMcA1LOoDQTvwfXh7lYma-UyF?v3)

