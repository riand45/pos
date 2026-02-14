import React from "react"
import { Helmet } from "react-helmet"
import "../styles/global.css"

const Layout = ({ children, title }) => {
  return (
    <div className="dark">
      <Helmet htmlAttributes={{ lang: 'id', class: 'dark' }}>
        <title>{title ? `${title} | POS Stream` : "POS Stream - Transformasi Bisnis dengan Kasir Digital Modern"}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800;900&display=swap" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-25..0&display=swap" rel="stylesheet"/>
      </Helmet>

      {/* Header / Navbar */}
      <header className="sticky top-0 z-50 w-full border-b border-white/10 bg-background-dark/80 backdrop-blur-md">
        <div className="max-w-7xl mx-auto px-6 h-20 flex items-center justify-between">
          <div className="flex items-center gap-2 group cursor-pointer" onClick={() => window.location.href = '/'}>
            <div className="bg-primary p-1.5 rounded-lg">
              <span className="material-symbols-outlined text-background-dark font-bold">account_balance_wallet</span>
            </div>
            <h1 className="text-xl font-bold tracking-tight text-white">POS <span className="text-primary">Stream</span></h1>
          </div>
          <nav className="hidden md:flex items-center gap-10">
            <a className="text-sm font-medium text-slate-300 hover:text-primary transition-colors" href="/features">Fitur</a>
            <a className="text-sm font-medium text-slate-300 hover:text-primary transition-colors" href="/#solusi">Solusi</a>
            <a className="text-sm font-medium text-slate-300 hover:text-primary transition-colors" href="/#target">Target Bisnis</a>
            <a className="text-sm font-medium text-slate-300 hover:text-primary transition-colors" href="/pricing">Harga</a>
          </nav>
          <div className="flex items-center gap-4">
            <button className="hidden sm:block text-sm font-semibold text-white hover:text-primary transition-colors px-4 py-2">Login</button>
            <a href="/contact" className="bg-primary hover:bg-primary/90 text-background-dark font-bold py-2.5 px-6 rounded-lg text-sm transition-all shadow-lg shadow-primary/20">
              Coba Gratis
            </a>
          </div>
        </div>
      </header>

      <main>{children}</main>

      {/* Footer */}
      <footer className="py-16 border-t border-white/5 bg-background-dark">
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 md:grid-cols-4 gap-12">
          <div className="flex flex-col gap-6">
            <div className="flex items-center gap-2">
              <div className="bg-primary p-1 rounded-md">
                <span className="material-symbols-outlined text-background-dark text-lg font-bold">account_balance_wallet</span>
              </div>
              <h1 className="text-lg font-bold tracking-tight text-white">POS <span className="text-primary">Stream</span></h1>
            </div>
            <p className="text-slate-500 text-sm leading-relaxed">
              Solusi point-of-sale modern untuk akselerasi pertumbuhan bisnis UMKM di seluruh Indonesia.
            </p>
            <div className="flex gap-4">
              <a className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400" href="#" aria-label="Facebook">
                <span className="material-symbols-outlined text-xl">public</span>
              </a>
              <a className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400" href="#" aria-label="Instagram">
                <span className="material-symbols-outlined text-xl">photo_camera</span>
              </a>
              <a className="w-10 h-10 rounded-full bg-white/5 flex items-center justify-center hover:bg-primary/20 hover:text-primary transition-all text-slate-400" href="#" aria-label="WhatsApp">
                <span className="material-symbols-outlined text-xl">chat</span>
              </a>
            </div>
          </div>
          <div className="flex flex-col gap-6">
            <h6 className="text-white font-bold uppercase tracking-widest text-xs">Produk</h6>
            <nav className="flex flex-col gap-4">
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/features">Fitur Kasir</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/features">Manajemen Stok</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/features">Dashboard Laporan</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="#">Add-ons</a>
            </nav>
          </div>
          <div className="flex flex-col gap-6">
            <h6 className="text-white font-bold uppercase tracking-widest text-xs">Perusahaan</h6>
            <nav className="flex flex-col gap-4">
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/about">Tentang Kami</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="#">Karir</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/contact">Hubungi Kami</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="/blog">Blog</a>
            </nav>
          </div>
          <div className="flex flex-col gap-6">
            <h6 className="text-white font-bold uppercase tracking-widest text-xs">Legal</h6>
            <nav className="flex flex-col gap-4">
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="#">Kebijakan Privasi</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="#">Syarat & Ketentuan</a>
              <a className="text-slate-500 hover:text-white transition-colors text-sm" href="#">SLA Bisnis</a>
            </nav>
          </div>
        </div>
        <div className="max-w-7xl mx-auto px-6 pt-16 mt-16 border-t border-white/5 flex flex-col md:flex-row justify-between items-center gap-4">
          <p className="text-slate-600 text-xs">© {new Date().getFullYear()} POS Stream Indonesia. Seluruh hak cipta dilindungi.</p>
          <div className="flex items-center gap-2">
            <span className="w-2 h-2 rounded-full bg-green-500 animate-pulse"></span>
            <span className="text-slate-600 text-[10px] font-bold uppercase tracking-tighter">Sistem Status: Operasional Normal</span>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default Layout
