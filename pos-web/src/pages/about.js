import React from "react"
import Layout from "../components/layout"

const AboutPage = () => {
  return (
    <Layout title="Tentang Kami">
      <section className="py-24 bg-background-dark min-h-screen">
        <div className="max-w-4xl mx-auto px-6">
          <div className="space-y-16">
            <div className="text-center space-y-6">
              <h1 className="text-5xl lg:text-7xl font-black text-white leading-tight">Misi Kami: <span className="text-primary">Digitalisasi UMKM</span></h1>
              <p className="text-xl text-slate-400">Menyediakan akses teknologi kasir enterprise untuk setiap lapisan pengusaha di Indonesia.</p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div className="p-8 rounded-3xl bg-[#192d33] border border-white/10 space-y-4">
                <span className="material-symbols-outlined text-4xl text-primary">psychology</span>
                <h3 className="text-xl font-bold text-white">Visi Kami</h3>
                <p className="text-slate-400 leading-relaxed">Menjadi platform POS nomor satu yang paling mudah digunakan dan paling andal bagi seluruh pengusaha retail dan F&B di Asia Tenggara.</p>
              </div>
              <div className="p-8 rounded-3xl bg-[#192d33] border border-white/10 space-y-4">
                <span className="material-symbols-outlined text-4xl text-primary">rocket_launch</span>
                <h3 className="text-xl font-bold text-white">Misi Kami</h3>
                <p className="text-slate-400 leading-relaxed">Membangun ekosistem bisnis digital yang sederhana namun kuat untuk membantu pemilik bisnis menghemat waktu dan meningkatkan profit.</p>
              </div>
            </div>

            <div className="space-y-8">
              <h2 className="text-3xl font-bold text-white border-l-4 border-primary pl-6 flex items-center gap-4">
                <span className="material-symbols-outlined text-primary">history_edu</span>
                Cerita Di Balik POS Stream
              </h2>
              <div className="space-y-6 text-lg text-slate-400 leading-relaxed">
                <p>Didirikan pada tahun 2024, POS Stream lahir dari observasi kami terhadap sulitnya pengusaha kecil mengelola laporan keuangan dan stok secara manual.</p>
                <p>Kami meyakini bahwa teknologi tidak seharusnya menjadi penghalang bagi bisnis untuk berkembang. Itulah mengapa kami fokus pada pengembangan antarmuka yang intuitif dan sistem cloud yang tangguh.</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default AboutPage
