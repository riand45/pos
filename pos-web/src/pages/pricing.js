import React from "react"
import Layout from "../components/layout"

const PricingPage = () => {
  const plans = [
    { name: "Gratis", price: "0", features: ["1 User", "Ganti Logo Struk", "Laporan Harian", "Max 50 Produk"], popular: false },
    { name: "Pro UMKM", price: "25rb", features: ["Unlimited Produk", "Laporan Laba Rugi", "Multi-User", "Manajemen Stok", "Support 24/7"], popular: true },
    { name: "Enterprise", price: "Custom", features: ["Multi-Cabang", "Integrasi API", "White-label App", "Training On-site"], popular: false }
  ]

  return (
    <Layout title="Paket Harga">
      <section className="py-24 bg-background-dark">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center mb-16">
            <h1 className="text-4xl lg:text-6xl font-black text-white mb-6">Investasi Cerdas untuk Bisnis Anda</h1>
            <p className="text-xl text-slate-400 max-w-3xl mx-auto">Pilih paket yang paling sesuai dengan skala usaha Anda.</p>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            {plans.map((p, i) => (
              <div key={i} className={`p-10 rounded-[32px] flex flex-col items-center text-center transition-all ${p.popular ? 'bg-gradient-to-br from-primary to-blue-700 border-none shadow-2xl shadow-primary/30 scale-105 z-10' : 'bg-[#192d33] border border-white/10'}`}>
                {p.popular && <span className="bg-white/20 text-white text-xs font-black uppercase tracking-widest px-4 py-1 rounded-full mb-6">Paling Populer</span>}
                <h3 className={`text-2xl font-bold mb-4 ${p.popular ? 'text-background-dark' : 'text-white'}`}>{p.name}</h3>
                <div className={`text-5xl font-black mb-8 flex items-baseline gap-1 ${p.popular ? 'text-background-dark' : 'text-white'}`}>
                  <span className="text-lg font-bold opacity-70">Rp</span> {p.price} <span className="text-lg font-bold opacity-70">{p.price !== 'Custom' ? '/bln' : ''}</span>
                </div>
                <ul className="space-y-4 mb-10 text-left w-full">
                  {p.features.map((f, j) => (
                    <li key={j} className={`flex items-center gap-3 text-sm font-medium ${p.popular ? 'text-background-dark/80' : 'text-slate-400'}`}>
                      <span className={`material-symbols-outlined text-lg ${p.popular ? 'text-background-dark' : 'text-primary'}`}>check_circle</span>
                      {f}
                    </li>
                  ))}
                </ul>
                <a href="/contact" className={`w-full py-4 rounded-xl font-black transition-all ${p.popular ? 'bg-background-dark text-white hover:opacity-90' : 'bg-primary text-background-dark hover:bg-primary/90'}`}>
                  Pilih Paket
                </a>
              </div>
            ))}
          </div>
          <p className="text-center mt-12 text-slate-500 text-sm font-medium">Trial 14 hari gratis tanpa kartu kredit untuk semua paket.</p>
        </div>
      </section>
    </Layout>
  )
}

export default PricingPage
