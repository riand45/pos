import React from "react"
import Layout from "../components/layout"

const IndexPage = () => {
  return (
    <Layout>
      {/* Hero Section */}
      <section className="relative pt-16 pb-24 lg:pt-32 lg:pb-40 overflow-hidden bg-background-dark">
        <div className="absolute top-0 left-1/2 -translate-x-1/2 w-full h-full pointer-events-none overflow-hidden opacity-20">
          <div className="absolute top-[-10%] left-[-10%] w-[40%] h-[40%] bg-primary rounded-full blur-[120px]"></div>
          <div className="absolute bottom-[-10%] right-[-10%] w-[30%] h-[30%] bg-blue-500 rounded-full blur-[100px]"></div>
        </div>
        <div className="max-w-7xl mx-auto px-6 grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
          <div className="relative z-10 flex flex-col gap-8">
            <div className="inline-flex items-center gap-2 px-3 py-1 rounded-full bg-primary/10 border border-primary/20 text-primary text-xs font-bold uppercase tracking-wider w-fit">
              <span className="relative flex h-2 w-2">
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-primary opacity-75"></span>
                <span className="relative inline-flex rounded-full h-2 w-2 bg-primary"></span>
              </span>
              Sistem Kasir Cloud #1 di Indonesia
            </div>
            <h1 className="text-5xl lg:text-7xl font-black text-white leading-[1.1] tracking-tight">
              Transformasi Bisnis Anda dengan <span className="text-primary">Kasir Digital</span> Modern
            </h1>
            <p className="text-lg lg:text-xl text-slate-400 leading-relaxed max-w-xl">
              Kelola penjualan, inventaris, dan pesanan dalam satu platform yang responsif dan mudah digunakan. Dirancang khusus untuk efisiensi bisnis Anda.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 pt-4">
              <button className="bg-primary hover:bg-primary/90 text-background-dark text-lg font-bold py-4 px-8 rounded-xl transition-all flex items-center justify-center gap-2 shadow-xl shadow-primary/20 group">
                Mulai Demo Gratis
                <span className="material-symbols-outlined group-hover:translate-x-1 transition-transform">arrow_forward</span>
              </button>
              <button className="bg-white/5 hover:bg-white/10 text-white text-lg font-bold py-4 px-8 rounded-xl border border-white/10 transition-all backdrop-blur-sm">
                Pelajari Fitur
              </button>
            </div>
            <div className="flex items-center gap-6 pt-6 border-t border-white/5">
              <div className="flex -space-x-3">
                <div className="w-10 h-10 rounded-full border-2 border-background-dark bg-slate-500 flex items-center justify-center text-[10px] font-bold">JD</div>
                <div className="w-10 h-10 rounded-full border-2 border-background-dark bg-primary flex items-center justify-center text-[10px] font-bold text-background-dark">AM</div>
                <div className="w-10 h-10 rounded-full border-2 border-background-dark bg-indigo-500 flex items-center justify-center text-[10px] font-bold">BK</div>
              </div>
              <p className="text-sm text-slate-400">Dipercaya oleh <span className="text-white font-bold">2,500+</span> pemilik bisnis UMKM</p>
            </div>
          </div>
          <div className="relative group">
            <div className="absolute -inset-1 bg-gradient-to-r from-primary to-blue-600 rounded-2xl blur opacity-25 group-hover:opacity-40 transition duration-1000 group-hover:duration-200"></div>
            <div className="relative bg-[#192d33] border border-white/10 rounded-2xl overflow-hidden shadow-2xl">
              <img alt="POS Stream Dashboard" className="w-full h-auto object-cover opacity-90 group-hover:scale-105 transition-transform duration-700" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDwEUbyD54IRv1vNIYl5cs26kWm-pBT9k0n80BR6DNd-oD4uqV25eW5nBGU4tcMig9Tf2eViP_hV1NXcgCKVGuSQy9yQKRwjNYpFib1aK2U7LKoRmVJuz7Ub0CHwuwWrgjMzhGkKmNaEmlW0dXG_KmrAgDEIZ5QLmWM8ivVoyR81qBryZwIu8aqHYZMMC-tWe46VALf6rO05WxG0KPse4ZYkU_9B0WHNgAXBEM0IqijADOYKT2tL2fhQPuyBGGoHxHj8NmPjjTWMnVZ"/>
              <div className="absolute bottom-4 right-4 bg-primary/90 p-4 rounded-xl shadow-lg backdrop-blur-md border border-white/20">
                <div className="flex items-center gap-3">
                  <div className="bg-background-dark p-2 rounded-lg">
                    <span className="material-symbols-outlined text-primary">trending_up</span>
                  </div>
                  <div>
                    <p className="text-[10px] text-background-dark font-bold uppercase tracking-widest opacity-70">Total Omzet</p>
                    <p className="text-xl font-black text-background-dark tracking-tighter">Rp 245.850.000</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Problem & Solution Section */}
      <section className="py-24 bg-white/5 border-y border-white/5 bg-background-dark" id="solusi">
        <div className="max-w-7xl mx-auto px-6">
          <div className="flex flex-col items-center text-center mb-20 gap-4">
            <h2 className="text-primary font-bold tracking-widest uppercase text-sm">Masalah & Solusi</h2>
            <h3 className="text-4xl lg:text-5xl font-black text-white max-w-2xl leading-tight">Tinggalkan Cara Manual yang Melelahkan</h3>
            <p className="text-slate-400 max-w-xl">Ubah hambatan operasional menjadi peluang pertumbuhan dengan sistem yang terintegrasi secara cerdas.</p>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div className="flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-red-500/30 transition-all group">
              <div className="w-14 h-14 rounded-xl bg-red-500/10 flex items-center justify-center group-hover:scale-110 transition-transform">
                <span className="material-symbols-outlined text-red-500 text-3xl">error</span>
              </div>
              <div className="flex flex-col gap-2">
                <h4 className="text-xl font-bold text-white">Kesalahan Pencatatan</h4>
                <p className="text-slate-400 leading-relaxed text-sm">Input manual sering menyebabkan selisih stok yang merugikan dan laporan keuangan yang berantakan.</p>
              </div>
              <div className="pt-4 mt-auto">
                <span className="text-primary text-xs font-bold flex items-center gap-2">SOLUSI POS STREAM <span className="material-symbols-outlined text-xs">arrow_forward</span></span>
                <p className="text-slate-200 text-sm font-medium mt-1">Otomatisasi pencatatan stok secara real-time setiap transaksi.</p>
              </div>
            </div>
            <div className="flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-orange-500/30 transition-all group">
              <div className="w-14 h-14 rounded-xl bg-orange-500/10 flex items-center justify-center group-hover:scale-110 transition-transform">
                <span className="material-symbols-outlined text-orange-500 text-3xl">timer_off</span>
              </div>
              <div className="flex flex-col gap-2">
                <h4 className="text-xl font-bold text-white">Pelayanan Lambat</h4>
                <p className="text-slate-400 leading-relaxed text-sm">Proses pemesanan tradisional membuat antrean mengular dan pelanggan merasa tidak nyaman.</p>
              </div>
              <div className="pt-4 mt-auto">
                <span className="text-primary text-xs font-bold flex items-center gap-2">SOLUSI POS STREAM <span className="material-symbols-outlined text-xs">arrow_forward</span></span>
                <p className="text-slate-200 text-sm font-medium mt-1">Antarmuka kasir super cepat untuk transaksi di bawah 10 detik.</p>
              </div>
            </div>
            <div className="flex flex-col gap-6 p-8 rounded-2xl bg-background-dark border border-white/10 hover:border-primary/30 transition-all group">
              <div className="w-14 h-14 rounded-xl bg-primary/10 flex items-center justify-center group-hover:scale-110 transition-transform">
                <span className="material-symbols-outlined text-primary text-3xl">bar_chart</span>
              </div>
              <div className="flex flex-col gap-2">
                <h4 className="text-xl font-bold text-white">Data Tidak Akurat</h4>
                <p className="text-slate-400 leading-relaxed text-sm">Sulit memantau performa harian tanpa adanya laporan otomatis yang akurat dan mudah diakses.</p>
              </div>
              <div className="pt-4 mt-auto">
                <span className="text-primary text-xs font-bold flex items-center gap-2">SOLUSI POS STREAM <span className="material-symbols-outlined text-xs">arrow_forward</span></span>
                <p className="text-slate-200 text-sm font-medium mt-1">Laporan dashboard interaktif yang bisa diakses via HP kapanpun.</p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-24 relative bg-background-dark" id="fitur">
        <div className="max-w-7xl mx-auto px-6">
          <div className="mb-20">
            <h3 className="text-4xl font-black text-white mb-4">Fitur Unggulan POS Stream</h3>
            <p className="text-slate-400 text-lg">Semua yang Anda butuhkan untuk mengelola operasional dalam satu genggaman.</p>
          </div>
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
            <div className="lg:row-span-2 p-8 rounded-3xl bg-gradient-to-br from-[#192d33] to-[#111e22] border border-white/10 flex flex-col gap-8 shadow-2xl relative overflow-hidden group">
              <div className="relative z-10">
                <h4 className="text-2xl font-bold text-white mb-2">Kanban Order Flow</h4>
                <p className="text-slate-400 mb-8">Pantau status setiap pesanan secara visual mulai dari masuk, diproses, hingga selesai di dapur atau retail.</p>
                <div className="grid grid-cols-3 gap-3">
                  <div className="flex flex-col gap-2">
                    <span className="text-[10px] font-bold text-slate-500 uppercase tracking-widest pl-1">Antrean</span>
                    <div className="p-3 bg-white/5 rounded-lg border border-white/10 shadow-sm">
                      <p className="text-xs font-bold text-white">#1202 - Latte</p>
                      <p className="text-[9px] text-slate-500">Meja 05</p>
                    </div>
                  </div>
                  <div className="flex flex-col gap-2">
                    <span className="text-[10px] font-bold text-orange-500 uppercase tracking-widest pl-1">Proses</span>
                    <div className="p-3 bg-orange-500/10 rounded-lg border border-orange-500/20 shadow-sm">
                      <p className="text-xs font-bold text-orange-500">#1201 - Pasta</p>
                      <div className="w-full bg-slate-700 h-1 mt-2 rounded-full overflow-hidden">
                        <div className="bg-orange-500 h-full w-[65%]"></div>
                      </div>
                    </div>
                  </div>
                  <div className="flex flex-col gap-2">
                    <span className="text-[10px] font-bold text-green-500 uppercase tracking-widest pl-1">Selesai</span>
                    <div className="p-3 bg-green-500/10 rounded-lg border border-green-500/20 shadow-sm">
                      <p className="text-xs font-bold text-green-500">#1199 - Black</p>
                      <span className="material-symbols-outlined text-xs text-green-500 mt-1">check_circle</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="p-6 rounded-2xl glass-card flex flex-col gap-4 group hover:scale-[1.02] transition-all">
                <div className="w-12 h-12 rounded-lg bg-primary/20 flex items-center justify-center">
                  <span className="material-symbols-outlined text-primary">category</span>
                </div>
                <h4 className="text-lg font-bold text-white">Manajemen Kategori</h4>
                <p className="text-sm text-slate-400 leading-relaxed">Organisasi katalog produk tanpa batas. Tambah, edit, dan atur varian produk hanya dalam hitungan detik.</p>
              </div>
              <div className="p-6 rounded-2xl glass-card flex flex-col gap-4 group hover:scale-[1.02] transition-all">
                <div className="w-12 h-12 rounded-lg bg-primary/20 flex items-center justify-center">
                  <span className="material-symbols-outlined text-primary">payments</span>
                </div>
                <h4 className="text-lg font-bold text-white">Transaksi Akurat</h4>
                <p className="text-sm text-slate-400 leading-relaxed">Kalkulasi pajak dan diskon otomatis. Mendukung Cash, QRIS, dan Kartu.</p>
              </div>
            </div>
            <div className="p-8 rounded-3xl glass-card flex flex-col md:flex-row items-center gap-8">
              <div className="flex-1 space-y-4">
                <h4 className="text-2xl font-bold text-white">Laporan Real-time Dashboard</h4>
                <p className="text-slate-400 leading-relaxed">Pantau omzet harian, produk terlaris, dan kinerja karyawan langsung dari smartphone Anda.</p>
                <ul className="grid grid-cols-2 gap-y-2 gap-x-4">
                  <li className="flex items-center gap-2 text-sm text-slate-300">
                    <span className="material-symbols-outlined text-primary text-lg">check_circle</span>
                    Analitik Harian
                  </li>
                  <li className="flex items-center gap-2 text-sm text-slate-300">
                    <span className="material-symbols-outlined text-primary text-lg">check_circle</span>
                    Export Excel/PDF
                  </li>
                  <li className="flex items-center gap-2 text-sm text-slate-300">
                    <span className="material-symbols-outlined text-primary text-lg">check_circle</span>
                    Manajemen Stok
                  </li>
                  <li className="flex items-center gap-2 text-sm text-slate-300">
                    <span className="material-symbols-outlined text-primary text-lg">check_circle</span>
                    Monitoring Laba
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Target Users Section */}
      <section className="py-24 bg-background-dark" id="target">
        <div className="max-w-7xl mx-auto px-6">
          <div className="text-center mb-16">
            <h3 className="text-4xl font-black text-white mb-4">Solusi Untuk Berbagai Bisnis</h3>
            <p className="text-slate-400">POS Stream didesain fleksibel untuk memenuhi kebutuhan spesifik berbagai sektor UMKM.</p>
          </div>
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-8">
            <div className="group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors">
              <img alt="Cafe" className="absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all" src="https://lh3.googleusercontent.com/aida-public/AB6AXuAhQrV2z6LNn5sj-O-p6nupo1RKFmprQg2hWyceS_DjSoQ-eJqNTJpmOySSF0jjq-a_0EeCLe4E-xrkEIKTGyGBWj7_SP4ZcDfdx6i4ZnTBedTFucUcavOvvPP5vdxmVTOej8jzHRJEz-9hDsMWh1ccwtTmh6R1Ws65dXKS_fdq9Zk158zXz6rQyhMTn8WV_v5H2iArOKgij_TMgIunIlXY-xDS3PBBzfv_IImzVB2Bb6MRUK8ztB4QPu2emOqyAs_75l6s7Gc5DHAW"/>
              <span className="material-symbols-outlined text-4xl text-primary relative z-10">local_cafe</span>
              <h6 className="text-xl font-bold text-white relative z-10">Cafe & Resto</h6>
            </div>
            <div className="group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors">
              <img alt="Retail" className="absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all" src="https://lh3.googleusercontent.com/aida-public/AB6AXuD1qsklX0wdRX31YdFOpvDq4VYf_2w0OlpHrsQsSx_JyfbbSJQpJ8eqBku0PS8WFa5LgtMrKzCtfW4L9D3Unz3mBmr1xLf_JgG2_f-vy_vnhvJAnZLqTE8sQ0C6Q8clFSy0LimMhfPkyVmXHrVHBiuJ1XW0_wiAcIeCeoRopE4yjLimkIgiK5Fka7JIHRbs2k1FU-QtqKuIdXEqL5FWh9hYfiZNDoNELeCqfp3I4A9g2Ks5buqzZuRrr7W3Y7PzPHKSAGPEXQPV1-jy"/>
              <span className="material-symbols-outlined text-4xl text-primary relative z-10">shopping_bag</span>
              <h6 className="text-xl font-bold text-white relative z-10">Retail Clothing</h6>
            </div>
            <div className="group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors">
              <img alt="Kelontong" className="absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all" src="https://lh3.googleusercontent.com/aida-public/AB6AXuD2ahZLe5tChLmSpa15ZOSvYnXCEK51X9wRIoZfS7JnlmIJK6i-kk0ujM7z0u7esvWCmzL9w_iQ6WM_bzH27b1OJNVXMYBdvTXAxDNtCOH9Ce2AAYrdeSIuIcJpzUd2YcIg6c2CBBMgbzPFYK2GXa7dkguFTOjkRtJnom8ud14cqC6C0LoIz7D0OWzsnCCZxk5cA0cXvhULZd-EVDxwYKGRep9MeCVkDTm_Ha6JbBQsGtg44gFwl_KOWaRtUovDPxVq5X-Y1EH5ayCk"/>
              <span className="material-symbols-outlined text-4xl text-primary relative z-10">store</span>
              <h6 className="text-xl font-bold text-white relative z-10">Toko Kelontong</h6>
            </div>
            <div className="group relative aspect-square rounded-3xl overflow-hidden border border-white/10 flex flex-col items-center justify-center gap-3 hover:border-primary/50 transition-colors">
              <img alt="Layanan" className="absolute inset-0 w-full h-full object-cover opacity-30 group-hover:scale-110 group-hover:opacity-50 transition-all" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDCQGoQl7R9a_X5Cg9b5-OxUzc2AH4juw510RXv-UdL7Puo6YYboV8_XmF920xqKW_16vJcaanNCA7WDs6FS_aMiNcpiurhQLHmuFnzrabUwj6uOJQmTZD2acGcjtGTlL3sFOnQM3lAahLvjoDJzo_yiK-QZ6ewNV-gY0asz3svXh9BLJyMgjhGHDv8etS2_8aJQ8b2WAxPagEOkIX7kq-2HI1Pz-YPVQfsxoUndLZnkKYJgeXum8IkqPuiEriIHwwRl9HtTF-08_6h"/>
              <span className="material-symbols-outlined text-4xl text-primary relative z-10">content_cut</span>
              <h6 className="text-xl font-bold text-white relative z-10">Jasa & Service</h6>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-24 px-6 bg-background-dark">
        <div className="max-w-5xl mx-auto p-12 lg:p-20 rounded-[40px] bg-gradient-to-br from-primary to-blue-700 relative overflow-hidden shadow-2xl shadow-primary/30">
          <div className="absolute top-0 right-0 p-20 opacity-10 text-white">
            <span className="material-symbols-outlined text-[300px] -rotate-12 translate-x-20">rocket_launch</span>
          </div>
          <div className="relative z-10 text-center flex flex-col items-center gap-8">
            <h2 className="text-3xl lg:text-5xl font-black text-background-dark leading-tight">Siap Tingkatkan Efisiensi Bisnis Anda?</h2>
            <p className="text-lg lg:text-xl text-background-dark/80 font-medium max-w-2xl">
              Jangan biarkan sistem lama menghambat pertumbuhan Anda. Hubungi kami sekarang untuk demo gratis.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 w-full justify-center">
              <a href="/contact" className="bg-background-dark hover:bg-background-dark/90 text-white font-bold py-5 px-10 rounded-2xl text-lg shadow-xl transition-all flex items-center justify-center gap-3">
                Dapatkan Demo Gratis
                <span className="material-symbols-outlined">support_agent</span>
              </a>
              <a href="https://wa.me/628123456789" className="bg-white/20 hover:bg-white/30 text-background-dark font-bold py-5 px-10 rounded-2xl text-lg transition-all border border-white/20">
                Chat WhatsApp
              </a>
            </div>
            <p className="text-background-dark/60 text-sm font-bold uppercase tracking-widest">Trial 14 Hari Tanpa Kartu Kredit</p>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default IndexPage
