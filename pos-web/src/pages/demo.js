import React from "react"
import Layout from "../components/layout"

const DemoPage = () => {
  return (
    <Layout title="Demo & Snapshot">
      <section className="py-24 bg-background-dark min-h-screen">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center mb-20 space-y-6">
            <h1 className="text-5xl lg:text-7xl font-black text-white leading-tight">Lihat <span className="text-primary">POS Stream</span> Beraksi</h1>
            <p className="text-xl text-slate-400 max-w-2xl mx-auto">Tampilan antarmuka yang modern, responsif, dan sangat mudah digunakan.</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 mb-24">
            <div className="space-y-6">
              <div className="aspect-[9/16] rounded-[32px] border-[12px] border-[#0f172a] bg-[#192d33] overflow-hidden shadow-2xl relative group">
                <div className="absolute inset-0 flex items-center justify-center bg-slate-800">
                   <span className="material-symbols-outlined text-6xl text-white/5 group-hover:text-primary/20 transition-all">dashboard</span>
                </div>
              </div>
              <div className="text-center">
                <h3 className="text-xl font-bold text-white">Dashboard Utama</h3>
                <p className="text-sm text-slate-500">Ringkasan bisnis Anda dalam sekejap.</p>
              </div>
            </div>

            <div className="space-y-6 lg:mt-12">
              <div className="aspect-[9/16] rounded-[32px] border-[12px] border-[#0f172a] bg-[#192d33] overflow-hidden shadow-2xl relative group">
                <div className="absolute inset-0 flex items-center justify-center bg-slate-800">
                   <span className="material-symbols-outlined text-6xl text-white/5 group-hover:text-primary/20 transition-all">receipt_long</span>
                </div>
              </div>
              <div className="text-center">
                <h3 className="text-xl font-bold text-white">Input Transaksi</h3>
                <p className="text-sm text-slate-500">Proses pesanan di bawah 10 detik.</p>
              </div>
            </div>

            <div className="space-y-6">
              <div className="aspect-[9/16] rounded-[32px] border-[12px] border-[#0f172a] bg-[#192d33] overflow-hidden shadow-2xl relative group">
                <div className="absolute inset-0 flex items-center justify-center bg-slate-800">
                   <span className="material-symbols-outlined text-6xl text-white/5 group-hover:text-primary/20 transition-all">monitoring</span>
                </div>
              </div>
              <div className="text-center">
                <h3 className="text-xl font-bold text-white">Analisa Penjualan</h3>
                <p className="text-sm text-slate-500">Laporan laba rugi instan & akurat.</p>
              </div>
            </div>
          </div>

          <div className="max-w-5xl mx-auto bg-gradient-to-br from-[#192d33] to-background-dark p-2 rounded-[40px] border border-white/10 shadow-3xl overflow-hidden">
            <div className="aspect-video bg-black flex items-center justify-center rounded-[38px] group relative cursor-pointer">
              <span className="material-symbols-outlined text-8xl text-primary animate-pulse group-hover:scale-125 transition-transform">play_circle</span>
              <div className="absolute inset-0 bg-primary/5 group-hover:bg-transparent transition-all"></div>
              <p className="absolute bottom-10 left-1/2 -translate-x-1/2 text-white font-black uppercase tracking-widest opacity-50">Tonton Video Demo Lengkap</p>
            </div>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default DemoPage
