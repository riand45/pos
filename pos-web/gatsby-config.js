module.exports = {
  siteMetadata: {
    title: `Aplikasi Kasir Android - Solusi POS UMKM Indonesia`,
    description: `Aplikasi kasir android murah dan lengkap untuk toko, retail, dan restoran. Tingkatkan efisiensi bisnis Anda sekarang.`,
    author: `@antigravity`,
    siteUrl: `https://gatsbystarterdefaultsource.gatsbyjs.io/`,
  },
  plugins: [
    `gatsby-plugin-postcss`,
    `gatsby-plugin-react-helmet`,
    `gatsby-plugin-image`,
    {
      resolve: `gatsby-source-filesystem`,
      options: {
        name: `images`,
        path: `${__dirname}/src/images`,
      },
    },
    `gatsby-transformer-sharp`,
    `gatsby-plugin-sharp`,
  ],
}
