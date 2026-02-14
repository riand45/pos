import React from "react"
import Layout from "../../components/layout"

const SegmentCafe = () => {
  return (
    <Layout title="POS untuk Cafe">
      <section className="py-24 bg-background-dark min-h-screen">
        <div className="max-w-4xl mx-auto px-6">
          <div className="space-y-12">
            <div className="space-y-6">
              <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-primary/10 border border-primary/20 text-primary text-[10px] font-black uppercase tracking-widest">
                Segmen Bisnis: Cafe & Resto
              </div>
              <h1 className="text-4xl lg:text-7xl font-black text-white leading-tight">Solusi Kasir Untuk <span className="text-primary">Cafe Modern</span></h1>
              <p className="text-xl text-slate-400 leading-relaxed">Dari coffee shop kecil hingga restoran multi-cabang, POS Stream membantu Anda mengelola pesanan secepat kilat.</p>
            </div>

            <div className="aspect-video rounded-[40px] overflow-hidden border border-white/10 relative group shadow-2xl">
              <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuAhQrV2z6LNn5sj-O-p6nupo1RKFmprQg2hWyceS_DjSoQ-eJqNTJpmOySSF0jjq-a_0EeCLe4E-xrkEIKTGyGBWj7_SP4ZcDfdx6i4ZnTBedTFucUcavOvvPP5vdxmVTOej8jzHRJEz-9hDsMWh1ccwtTmh6R1Ws65dXKS_fdq9Zk158zXz6rQyhMTn8WV_v5H2iArOKgij_TMgIunIlXY-xDS3PBBzfv_IImzVB2Bb6MRUK8ztB4QPu2emOqyAs_75l6s7Gc5DHAW" alt="Cafe POS" className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-700 opacity-80" />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-12 pt-12">
              <div className="space-y-6">
                <h3 className="text-2xl font-bold text-white flex items-center gap-3">
                  <span className="material-symbols-outlined text-primary">restaurant_menu</span>
                  Fitur Unggulan
                </h3>
                <ul className="space-y-4">
                  <li className="flex items-start gap-3">
                    <span className="material-symbols-outlined text-primary text-sm mt-1">check_circle</span>
                    <span className="text-slate-300"><strong>Kitchen Display:</strong> Pesanan langsung terkirim ke barista/dapur.</span>
                  </li>
                  <li className="flex items-start gap-3">
                    <span className="material-symbols-outlined text-primary text-sm mt-1">check_circle</span>
                    <span className="text-slate-300"><strong>Split Bill:</strong> Pelanggan bisa bayar sendiri-sendiri dengan mudah.</span>
                  </li>
                  <li className="flex items-start gap-3">
                    <span className="material-symbols-outlined text-primary text-sm mt-1">check_circle</span>
                    <span className="text-slate-300"><strong>Manajemen Meja:</strong> Pantau meja yang kosong dan yang sudah terisi.</span>
                  </li>
                </ul>
              </div>
              <div className="bg-[#192d33] p-8 rounded-3xl border border-white/10 h-fit">
                <h3 className="text-xl font-bold text-white mb-4">Konsultasi Gratis</h3>
                <p className="text-slate-400 mb-8 text-sm leading-relaxed">Dapatkan demo khusus untuk sektor cafe dan lihat bagaimana sistem kami membantu barista Anda bekerja lebih cepat.</p>
                <a href="/contact" className="w-full inline-block text-center py-4 bg-primary text-background-dark font-black rounded-xl hover:opacity-90 transition-all">Hubungi Sekarang</a>
              </div>
            </div>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default SegmentCafe
