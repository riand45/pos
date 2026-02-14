import React from "react"
import Layout from "../components/layout"

const FeaturesPage = () => {
  const features = [
    { title: "Manajemen Produk", desc: "Kelola ribuan produk dengan kategori dan varian harga yang mudah dikustomisasi.", icon: "inventory_2" },
    { title: "Manajemen Stok", desc: "Monitoring stok masuk dan keluar secara otomatis. Notifikasi jika stok menipis.", icon: "inventory" },
    { title: "Laporan COGS/HPP", desc: "Hitung harga pokok penjualan secara akurat untuk mengetahui profit bersih Anda.", icon: "calculate" },
    { title: "Sistem Order", desc: "Alur kerja untuk restoran: Antrian -> Proses -> Selesai. Pantau status pesanan pelanggan.", icon: "receipt_long" },
    { title: "Manajemen Pelanggan", desc: "Catat database pelanggan untuk program loyalitas dan promo di masa depan.", icon: "groups" },
    { title: "Laporan Pengeluaran", desc: "Bukan cuma jualan, catat biaya operasional bisnis dalam satu aplikasi.", icon: "account_balance" }
  ]

  return (
    <Layout title="Fitur Lengkap">
      <section className="py-24 bg-background-dark">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center mb-16">
            <h1 className="text-4xl lg:text-6xl font-black text-white mb-6">Fitur Lengkap POS Stream</h1>
            <p className="text-xl text-slate-400 max-w-3xl mx-auto">Solusi teknologi terdepan untuk efisiensi maksimal bisnis Anda.</p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            {features.map((f, i) => (
              <div key={i} className="p-8 rounded-2xl bg-[#192d33] border border-white/10 hover:border-primary/50 transition-all group">
                <div className="w-14 h-14 rounded-xl bg-primary/10 flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                  <span className="material-symbols-outlined text-primary text-3xl">{f.icon}</span>
                </div>
                <h3 className="text-xl font-bold text-white mb-4">{f.title}</h3>
                <p className="text-slate-400 leading-relaxed">{f.desc}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="py-24 bg-white/5 bg-background-dark">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex flex-col lg:flex-row items-center gap-16">
            <div className="flex-1 space-y-8">
              <h2 className="text-4xl font-black text-white">Ekosistem Digital yang Terintegrasi</h2>
              <p className="text-lg text-slate-400">POS Stream bukan hanya sekadar mesin kasir. Ini adalah pusat kendali bisnis Anda yang menghubungkan setiap aspek operasional.</p>
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <div className="flex items-start gap-4">
                  <span className="material-symbols-outlined text-primary">cloud_sync</span>
                  <div>
                    <h4 className="text-white font-bold">Cloud Sync</h4>
                    <p className="text-sm text-slate-400">Data tersinkronisasi otomatis ke cloud secara real-time.</p>
                  </div>
                </div>
                <div className="flex items-start gap-4">
                  <span className="material-symbols-outlined text-primary">print</span>
                  <div>
                    <h4 className="text-white font-bold">Bluetooth Print</h4>
                    <p className="text-sm text-slate-400">Dukungan penuh untuk berbagai jenis printer kasir.</p>
                  </div>
                </div>
              </div>
            </div>
            <div className="flex-1 w-full aspect-video bg-[#192d33] rounded-3xl border border-white/10 shadow-2xl overflow-hidden relative group">
              <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuBKQTk0SvInNVbA8J8oruyuAjWo0pYT9zhqSkG-eXoXsFSo13AHyvbXVQ_HpYrjazzXAupqGe74cwu9l9_3XLghxiGgdP4BMgE8D2QY1rsnmYqyJ9p8aI1wrkXR4xRdz8yOIgkV2YbkgoVNh7_KAX4cYbLpiWPu1DZL2H1vLkWrsaXTLi8aDlUCQAL8hGnh6-u1c9uQCeGtzGIO7mTR4z3axI5pMxITIsWpGQoJdpUq2zKS04d1Bfqd8iVNU5FTbHgPbVZJoCEx-0nX" alt="Dashboard Preview" className="w-full h-full object-cover opacity-80 group-hover:scale-105 transition-transform duration-700" />
            </div>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default FeaturesPage
