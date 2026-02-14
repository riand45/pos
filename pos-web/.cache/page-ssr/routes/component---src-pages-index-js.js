exports.id = "component---src-pages-index-js";
exports.ids = ["component---src-pages-index-js"];
exports.modules = {

/***/ "./src/components/layout.js":
/*!**********************************!*\
  !*** ./src/components/layout.js ***!
  \**********************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ "react");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var react_helmet__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! react-helmet */ "./node_modules/react-helmet/es/Helmet.js");
/* harmony import */ var _styles_global_css__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../styles/global.css */ "./src/styles/global.css");
/* harmony import */ var _styles_global_css__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_styles_global_css__WEBPACK_IMPORTED_MODULE_2__);



const Layout = ({
  children,
  title
}) => {
  return /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "dark"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement(react_helmet__WEBPACK_IMPORTED_MODULE_1__.Helmet, {
    htmlAttributes: {
      lang: 'id',
      class: 'dark'
    }
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("title", null, title ? `${title} | POS Stream` : "POS Stream - Transformasi Bisnis dengan Kasir Digital Modern"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("meta", {
    name: "viewport",
    content: "width=device-width, initial-scale=1.0"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("link", {
    href: "https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap",
    rel: "stylesheet"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("link", {
    href: "https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@100..700,0..1&display=swap",
    rel: "stylesheet"
  })), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("header", {
    className: "sticky top-0 z-50 w-full border-b border-white/10 bg-background-dark/80 backdrop-blur-md"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6 h-20 flex items-center justify-between"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-2 group cursor-pointer",
    onClick: () => window.location.href = '/'
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "bg-primary p-1.5 rounded-lg"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-background-dark font-bold"
  }, "account_balance_wallet")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h1", {
    className: "text-xl font-bold tracking-tight text-white"
  }, "POS ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary"
  }, "Stream"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("nav", {
    className: "hidden md:flex items-center gap-10"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-sm font-medium text-slate-300 hover:text-primary transition-colors",
    href: "/features"
  }, "Fitur"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-sm font-medium text-slate-300 hover:text-primary transition-colors",
    href: "/#solusi"
  }, "Solusi"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-sm font-medium text-slate-300 hover:text-primary transition-colors",
    href: "/#target"
  }, "Target Bisnis"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-sm font-medium text-slate-300 hover:text-primary transition-colors",
    href: "/pricing"
  }, "Harga")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("button", {
    className: "hidden sm:block text-sm font-semibold text-white hover:text-primary transition-colors px-4 py-2"
  }, "Login"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    href: "/contact",
    className: "bg-primary hover:bg-primary/90 text-background-dark font-bold py-2.5 px-6 rounded-lg text-sm transition-all shadow-lg shadow-primary/20"
  }, "Coba Gratis")))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("main", null, children), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("footer", {
    className: "py-16 border-t border-white/5 bg-background-dark"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-4 gap-12"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "bg-primary p-1 rounded-md"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-background-dark text-lg font-bold"
  }, "account_balance_wallet")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h1", {
    className: "text-lg font-bold tracking-tight text-white"
  }, "POS ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary"
  }, "Stream"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-500 text-sm leading-relaxed"
  }, "Solusi point-of-sale modern untuk akselerasi pertumbuhan bisnis UMKM di seluruh Indonesia."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400",
    href: "#",
    "aria-label": "Facebook"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xl"
  }, "public")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400",
    href: "#",
    "aria-label": "Instagram"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xl"
  }, "photo_camera")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400",
    href: "#",
    "aria-label": "WhatsApp"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xl"
  }, "chat")))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-white font-bold uppercase tracking-widest text-xs"
  }, "Produk"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("nav", {
    className: "flex flex-col gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/features"
  }, "Fitur Kasir"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/features"
  }, "Manajemen Stok"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/features"
  }, "Dashboard Laporan"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "#"
  }, "Add-ons"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-white font-bold uppercase tracking-widest text-xs"
  }, "Perusahaan"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("nav", {
    className: "flex flex-col gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/about"
  }, "Tentang Kami"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "#"
  }, "Karir"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/contact"
  }, "Hubungi Kami"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "/blog"
  }, "Blog"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-white font-bold uppercase tracking-widest text-xs"
  }, "Legal"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("nav", {
    className: "flex flex-col gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "#"
  }, "Kebijakan Privasi"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "#"
  }, "Syarat & Ketentuan"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    className: "text-slate-500 hover:text-white transition-colors text-sm",
    href: "#"
  }, "SLA Bisnis")))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6 pt-16 mt-16 border-t border-white/5 flex flex-col md:flex-row justify-between items-center gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-600 text-xs"
  }, "\xA9 ", new Date().getFullYear(), " POS Stream Indonesia. Seluruh hak cipta dilindungi."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "w-2 h-2 rounded-full bg-green-500 animate-pulse"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-slate-600 text-[10px] font-bold uppercase tracking-tighter"
  }, "Sistem Status: Operasional Normal")))));
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (Layout);

/***/ }),

/***/ "./src/pages/index.js?export=default":
/*!*******************************************!*\
  !*** ./src/pages/index.js?export=default ***!
  \*******************************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export */ __webpack_require__.d(__webpack_exports__, {
/* harmony export */   "default": () => (__WEBPACK_DEFAULT_EXPORT__)
/* harmony export */ });
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! react */ "react");
/* harmony import */ var react__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(react__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _components_layout__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../components/layout */ "./src/components/layout.js");


const IndexPage = () => {
  return /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement(_components_layout__WEBPACK_IMPORTED_MODULE_1__["default"], null, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("section", {
    className: "relative pt-16 pb-24 lg:pt-32 lg:pb-40 overflow-hidden bg-background-dark"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute top-0 left-1/2 -translate-x-1/2 w-full h-full pointer-events-none overflow-hidden opacity-20"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-primary rounded-full blur-[120px]"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute bottom-[-10%] right-[-10%] w-[30%] h-[30%] bg-blue-500 rounded-full blur-[100px]"
  })), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-16 items-center"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "relative z-10 flex flex-col gap-8"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "inline-flex items-center gap-2 px-3 py-1 rounded-full bg-primary/10 border border-primary/20 text-primary text-xs font-bold uppercase tracking-wider w-fit"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "relative flex h-2 w-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "animate-ping absolute inline-flex h-full w-full rounded-full bg-primary opacity-75"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "relative inline-flex rounded-full h-2 w-2 bg-primary"
  })), "Sistem Kasir Cloud #1 di Indonesia"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h1", {
    className: "text-5xl lg:text-7xl font-black text-white leading-[1.1] tracking-tight"
  }, "Transformasi Bisnis Anda dengan ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary"
  }, "Kasir Digital"), " Modern"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-lg lg:text-xl text-slate-400 leading-relaxed max-w-xl"
  }, "Kelola penjualan, inventaris, dan pesanan dalam satu platform yang responsif dan mudah digunakan. Dirancang khusus untuk efisiensi bisnis Anda."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col sm:flex-row gap-4 pt-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("button", {
    className: "bg-primary hover:bg-primary/90 text-background-dark text-lg font-bold py-4 px-8 rounded-xl transition-all flex items-center justify-center gap-2 shadow-xl shadow-primary/20 group"
  }, "Mulai Demo Gratis", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined group-hover:translate-x-1 transition-transform"
  }, "arrow_forward")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("button", {
    className: "bg-white/5 hover:bg-white/10 text-white text-lg font-bold py-4 px-8 rounded-xl border border-white/10 transition-all backdrop-blur-sm"
  }, "Pelajari Fitur")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-6 pt-6 border-t border-white/5"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex -space-x-3"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-10 h-10 rounded-full border-2 border-background-dark bg-slate-500 flex items-center justify-center text-[10px] font-bold"
  }, "JD"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-10 h-10 rounded-full border-2 border-background-dark bg-primary flex items-center justify-center text-[10px] font-bold text-background-dark"
  }, "AM"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-10 h-10 rounded-full border-2 border-background-dark bg-indigo-500 flex items-center justify-center text-[10px] font-bold"
  }, "BK")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-sm text-slate-400"
  }, "Dipercaya oleh ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-white font-bold"
  }, "2,500+"), " pemilik bisnis UMKM"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "relative group"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute -inset-1 bg-gradient-to-r from-primary to-blue-600 rounded-2xl blur opacity-25 group-hover:opacity-40 transition duration-1000 group-hover:duration-200"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "relative bg-[#192d33] border border-white/10 rounded-2xl overflow-hidden shadow-2xl"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("img", {
    alt: "POS Stream Dashboard",
    className: "w-full h-auto object-cover opacity-90 group-hover:scale-105 transition-transform duration-700",
    src: "https://lh3.googleusercontent.com/aida-public/AB6AXuDwEUbyD54IRv1vNIYl5cs26kWm-pBT9k0n80BR6DNd-oD4uqV25eW5nBGU4tcMig9Tf2eViP_hV1NXcgCKVGuSQy9yQKRwjNYpFib1aK2U7LKoRmVJuz7Ub0CHwuwWrgjMzhGkKmNaEmlW0dXG_KmrAgDEIZ5QLmWM8ivVoyR81qBryZwIu8aqHYZMMC-tWe46VALf6rO05WxG0KPse4ZYkU_9B0WHNgAXBEM0IqijADOYKT2tL2fhQPuyBGGoHxHj8NmPjjTWMnVZ"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute bottom-4 right-4 bg-primary/90 p-4 rounded-xl shadow-lg backdrop-blur-md border border-white/20"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex items-center gap-3"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "bg-background-dark p-2 rounded-lg"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary"
  }, "trending_up")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", null, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-[10px] text-background-dark font-bold uppercase tracking-widest opacity-70"
  }, "Total Omzet"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-xl font-black text-background-dark tracking-tighter"
  }, "Rp 245.850.000")))))))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("section", {
    className: "py-24 bg-white/5 border-y border-white/5 bg-background-dark",
    id: "solusi"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col items-center text-center mb-20 gap-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h2", {
    className: "text-primary font-bold tracking-widest uppercase text-sm"
  }, "Masalah & Solusi"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h3", {
    className: "text-4xl lg:text-5xl font-black text-white max-w-2xl leading-tight"
  }, "Tinggalkan Cara Manual yang Melelahkan"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 max-w-xl"
  }, "Ubah hambatan operasional menjadi peluang pertumbuhan dengan sistem yang terintegrasi secara cerdas.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "grid grid-cols-1 md:grid-cols-3 gap-8"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-red-500/30 transition-all group"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-14 h-14 rounded-xl bg-red-500/10 flex items-center justify-center group-hover:scale-110 transition-transform"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-red-500 text-3xl"
  }, "error")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-xl font-bold text-white"
  }, "Kesalahan Pencatatan"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 leading-relaxed text-sm"
  }, "Input manual sering menyebabkan selisih stok yang merugikan dan laporan keuangan yang berantakan.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "pt-4 mt-auto"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary text-xs font-bold flex items-center gap-2"
  }, "SOLUSI POS STREAM ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xs"
  }, "arrow_forward")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-200 text-sm font-medium mt-1"
  }, "Otomatisasi pencatatan stok secara real-time setiap transaksi."))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-orange-500/30 transition-all group"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-14 h-14 rounded-xl bg-orange-500/10 flex items-center justify-center group-hover:scale-110 transition-transform"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-orange-500 text-3xl"
  }, "timer_off")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-xl font-bold text-white"
  }, "Pelayanan Lambat"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 leading-relaxed text-sm"
  }, "Proses pemesanan tradisional membuat antrean mengular dan pelanggan merasa tidak nyaman.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "pt-4 mt-auto"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary text-xs font-bold flex items-center gap-2"
  }, "SOLUSI POS STREAM ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xs"
  }, "arrow_forward")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-200 text-sm font-medium mt-1"
  }, "Antarmuka kasir super cepat untuk transaksi di bawah 10 detik."))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-primary/30 transition-all group"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-14 h-14 rounded-xl bg-primary/10 flex items-center justify-center group-hover:scale-110 transition-transform"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary text-3xl"
  }, "bar_chart")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-xl font-bold text-white"
  }, "Data Tidak Akurat"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 leading-relaxed text-sm"
  }, "Sulit memantau performa harian tanpa adanya laporan otomatis yang akurat dan mudah diakses.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "pt-4 mt-auto"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-primary text-xs font-bold flex items-center gap-2"
  }, "SOLUSI POS STREAM ", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xs"
  }, "arrow_forward")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-200 text-sm font-medium mt-1"
  }, "Laporan dashboard interaktif yang bisa diakses via HP kapanpun.")))))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("section", {
    className: "py-24 relative bg-background-dark",
    id: "fitur"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "mb-20"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h3", {
    className: "text-4xl font-black text-white mb-4"
  }, "Fitur Unggulan POS Stream"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 text-lg"
  }, "Semua yang Anda butuhkan untuk mengelola operasional dalam satu genggaman.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "grid grid-cols-1 lg:grid-cols-2 gap-12"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "lg:row-span-2 p-8 rounded-3xl bg-gradient-to-br from-[#192d33] to-[#111e22] border border-white/10 flex flex-col gap-8 shadow-2xl relative overflow-hidden group"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "relative z-10"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-2xl font-bold text-white mb-2"
  }, "Kanban Order Flow"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 mb-8"
  }, "Pantau status setiap pesanan secara visual mulai dari masuk, diproses, hingga selesai di dapur atau retail."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "grid grid-cols-3 gap-3"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-[10px] font-bold text-slate-500 uppercase tracking-widest pl-1"
  }, "Antrean"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-3 bg-white/5 rounded-lg border border-white/10 shadow-sm"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-xs font-bold text-white"
  }, "#1202 - Latte"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-[9px] text-slate-500"
  }, "Meja 05"))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-[10px] font-bold text-orange-500 uppercase tracking-widest pl-1"
  }, "Proses"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-3 bg-orange-500/10 rounded-lg border border-orange-500/20 shadow-sm"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-xs font-bold text-orange-500"
  }, "#1201 - Pasta"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-full bg-slate-700 h-1 mt-2 rounded-full overflow-hidden"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "bg-orange-500 h-full w-[65%]"
  })))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col gap-2"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "text-[10px] font-bold text-green-500 uppercase tracking-widest pl-1"
  }, "Selesai"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-3 bg-green-500/10 rounded-lg border border-green-500/20 shadow-sm"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-xs font-bold text-green-500"
  }, "#1199 - Black"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-xs text-green-500 mt-1"
  }, "check_circle")))))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "grid grid-cols-1 md:grid-cols-2 gap-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-6 rounded-2xl glass-card flex flex-col gap-4 group hover:scale-[1.02] transition-all"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-12 h-12 rounded-lg bg-primary/20 flex items-center justify-center"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary"
  }, "category")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-lg font-bold text-white"
  }, "Manajemen Kategori"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-sm text-slate-400 leading-relaxed"
  }, "Organisasi katalog produk tanpa batas. Tambah, edit, dan atur varian produk hanya dalam hitungan detik.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-6 rounded-2xl glass-card flex flex-col gap-4 group hover:scale-[1.02] transition-all"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "w-12 h-12 rounded-lg bg-primary/20 flex items-center justify-center"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary"
  }, "payments")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-lg font-bold text-white"
  }, "Transaksi Akurat"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-sm text-slate-400 leading-relaxed"
  }, "Kalkulasi pajak dan diskon otomatis. Mendukung Cash, QRIS, dan Kartu."))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "p-8 rounded-3xl glass-card flex flex-col md:flex-row items-center gap-8"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex-1 space-y-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h4", {
    className: "text-2xl font-bold text-white"
  }, "Laporan Real-time Dashboard"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400 leading-relaxed"
  }, "Pantau omzet harian, produk terlaris, dan kinerja karyawan langsung dari smartphone Anda."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("ul", {
    className: "grid grid-cols-2 gap-y-2 gap-x-4"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("li", {
    className: "flex items-center gap-2 text-sm text-slate-300"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary text-lg"
  }, "check_circle"), "Analitik Harian"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("li", {
    className: "flex items-center gap-2 text-sm text-slate-300"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary text-lg"
  }, "check_circle"), "Export Excel/PDF"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("li", {
    className: "flex items-center gap-2 text-sm text-slate-300"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary text-lg"
  }, "check_circle"), "Manajemen Stok"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("li", {
    className: "flex items-center gap-2 text-sm text-slate-300"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-primary text-lg"
  }, "check_circle"), "Monitoring Laba"))))))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("section", {
    className: "py-24 bg-background-dark",
    id: "target"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-7xl mx-auto px-6"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "text-center mb-16"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h3", {
    className: "text-4xl font-black text-white mb-4"
  }, "Solusi Untuk Berbagai Bisnis"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-slate-400"
  }, "POS Stream didesain fleksibel untuk memenuhi kebutuhan spesifik berbagai sektor UMKM.")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "grid grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-8"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("img", {
    alt: "Cafe",
    className: "absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all",
    src: "https://lh3.googleusercontent.com/aida-public/AB6AXuAhQrV2z6LNn5sj-O-p6nupo1RKFmprQg2hWyceS_DjSoQ-eJqNTJpmOySSF0jjq-a_0EeCLe4E-xrkEIKTGyGBWj7_SP4ZcDfdx6i4ZnTBedTFucUcavOvvPP5vdxmVTOej8jzHRJEz-9hDsMWh1ccwtTmh6R1Ws65dXKS_fdq9Zk158zXz6rQyhMTn8WV_v5H2iArOKgij_TMgIunIlXY-xDS3PBBzfv_IImzVB2Bb6MRUK8ztB4QPu2emOqyAs_75l6s7Gc5DHAW"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-4xl text-primary relative z-10"
  }, "local_cafe"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-xl font-bold text-white relative z-10"
  }, "Cafe & Resto")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("img", {
    alt: "Retail",
    className: "absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all",
    src: "https://lh3.googleusercontent.com/aida-public/AB6AXuD1qsklX0wdRX31YdFOpvDq4VYf_2w0OlpHrsQsSx_JyfbbSJQpJ8eqBku0PS8WFa5LgtMrKzCtfW4L9D3Unz3mBmr1xLf_JgG2_f-vy_vnhvJAnZLqTE8sQ0C6Q8clFSy0LimMhfPkyVmXHrVHBiuJ1XW0_wiAcIeCeoRopE4yjLimkIgiK5Fka7JIHRbs2k1FU-QtqKuIdXEqL5FWh9hYfiZNDoNELeCqfp3I4A9g2Ks5buqzZuRrr7W3Y7PzPHKSAGPEXQPV1-jy"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-4xl text-primary relative z-10"
  }, "shopping_bag"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-xl font-bold text-white relative z-10"
  }, "Retail Clothing")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("img", {
    alt: "Kelontong",
    className: "absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all",
    src: "https://lh3.googleusercontent.com/aida-public/AB6AXuD2ahZLe5tChLmSpa15ZOSvYnXCEK51X9wRIoZfS7JnlmIJK6i-kk0ujM7z0u7esvWCmzL9w_iQ6WM_bzH27b1OJNVXMYBdvTXAxDNtCOH9Ce2AAYrdeSIuIcJpzUd2YcIg6c2CBBMgbzPFYK2GXa7dkguFTOjkRtJnom8ud14cqC6C0LoIz7D0OWzsnCCZxk5cA0cXvhULZd-EVDxwYKGRep9MeCVkDTm_Ha6JbBQsGtg44gFwl_KOWaRtUovDPxVq5X-Y1EH5ayCk"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-4xl text-primary relative z-10"
  }, "store"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-xl font-bold text-white relative z-10"
  }, "Toko Kelontong")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("img", {
    alt: "Layanan",
    className: "absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all",
    src: "https://lh3.googleusercontent.com/aida-public/AB6AXuDCQGoQl7R9a_X5Cg9b5-OxUzc2AH4juw510RXv-UdL7Puo6YYboV8_XmF920xqKW_16vJcaanNCA7WDs6FS_aMiNcpiurhQLHmuFnzrabUwj6uOJQmTZD2acGcjtGTlL3sFOnQM3lAahLvjoDJzo_yiK-QZ6ewNV-gY0asz3svXh9BLJyMgjhGHDv8etS2_8aJQ8b2WAxPagEOkIX7kq-2HI1Pz-YPVQfsxoUndLZnkKYJgeXum8IkqPuiEriIHwwRl9HtTF-08_6h"
  }), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-4xl text-primary relative z-10"
  }, "content_cut"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h6", {
    className: "text-xl font-bold text-white relative z-10"
  }, "Jasa & Service"))))), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("section", {
    className: "py-24 px-6 bg-background-dark"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "max-w-5xl mx-auto p-12 lg:p-20 rounded-[40px] bg-gradient-to-br from-primary to-blue-700 relative overflow-hidden shadow-2xl shadow-primary/30"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "absolute top-0 right-0 p-20 opacity-10 text-white"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined text-[300px] -rotate-12 translate-x-20"
  }, "rocket_launch")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "relative z-10 text-center flex flex-col items-center gap-8"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("h2", {
    className: "text-3xl lg:text-5xl font-black text-background-dark leading-tight"
  }, "Siap Tingkatkan Efisiensi Bisnis Anda?"), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-lg lg:text-xl text-background-dark/80 font-medium max-w-2xl"
  }, "Jangan biarkan sistem lama menghambat pertumbuhan Anda. Hubungi kami sekarang untuk demo gratis."), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("div", {
    className: "flex flex-col sm:flex-row gap-4 w-full justify-center"
  }, /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    href: "/contact",
    className: "bg-background-dark hover:bg-background-dark/90 text-white font-bold py-5 px-10 rounded-2xl text-lg shadow-xl transition-all flex items-center justify-center gap-3"
  }, "Dapatkan Demo Gratis", /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("span", {
    className: "material-symbols-outlined"
  }, "support_agent")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("a", {
    href: "https://wa.me/628123456789",
    className: "bg-white/20 hover:bg-white/30 text-background-dark font-bold py-5 px-10 rounded-2xl text-lg transition-all border border-white/20"
  }, "Chat WhatsApp")), /*#__PURE__*/react__WEBPACK_IMPORTED_MODULE_0___default().createElement("p", {
    className: "text-background-dark/60 text-sm font-bold uppercase tracking-widest"
  }, "Trial 14 Hari Tanpa Kartu Kredit")))));
};
/* harmony default export */ const __WEBPACK_DEFAULT_EXPORT__ = (IndexPage);

/***/ }),

/***/ "./src/styles/global.css":
/*!*******************************!*\
  !*** ./src/styles/global.css ***!
  \*******************************/
/***/ (() => {



/***/ })

};
;
//# sourceMappingURL=component---src-pages-index-js.js.map