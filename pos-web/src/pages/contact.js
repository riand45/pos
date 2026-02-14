import React from "react"
import Layout from "../components/layout"

const ContactPage = () => {
  return (
    <Layout title="Hubungi Kami">
      <section className="py-24 bg-background-dark min-h-screen">
        <div className="max-w-7xl mx-auto px-6">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-20 items-center">
            <div className="space-y-8">
              <h1 className="text-5xl lg:text-7xl font-black text-white leading-tight">Siap Untuk <span className="text-primary">Naik Level?</span></h1>
              <p className="text-xl text-slate-400 leading-relaxed">Tim ahli kami siap membantu Anda mengintegrasikan sistem kasir digital terbaik untuk bisnis Anda.</p>
              
              <div className="space-y-6 pt-8">
                <div className="flex items-center gap-6 group">
                  <div className="w-14 h-14 rounded-2xl bg-primary/10 flex items-center justify-center border border-primary/20 group-hover:bg-primary group-hover:text-background-dark transition-all">
                    <span className="material-symbols-outlined text-3xl">phone_in_talk</span>
                  </div>
                  <div>
                    <p className="text-xs font-bold text-slate-500 uppercase tracking-widest">WhatsApp Business</p>
                    <p className="text-xl font-bold text-white">+62 812 3456 789</p>
                  </div>
                </div>
                <div className="flex items-center gap-6 group">
                  <div className="w-14 h-14 rounded-2xl bg-primary/10 flex items-center justify-center border border-primary/20 group-hover:bg-primary group-hover:text-background-dark transition-all">
                    <span className="material-symbols-outlined text-3xl">mail</span>
                  </div>
                  <div>
                    <p className="text-xs font-bold text-slate-500 uppercase tracking-widest">Email Support</p>
                    <p className="text-xl font-bold text-white">halo@posstream.id</p>
                  </div>
                </div>
              </div>
            </div>

            <div className="bg-[#192d33] border border-white/10 p-10 lg:p-12 rounded-[40px] shadow-2xl relative">
              <div className="absolute -top-6 -right-6 w-24 h-24 bg-primary/20 rounded-full blur-2xl"></div>
              <form className="space-y-6 relative z-10">
                <div className="space-y-2">
                  <label className="text-sm font-bold text-white uppercase tracking-wider pl-1">Nama Lengkap</label>
                  <input type="text" placeholder="Contoh: Budi Santoso" className="w-full bg-background-dark border border-white/10 rounded-xl px-4 py-4 text-white focus:border-primary focus:ring-1 focus:ring-primary transition-all outline-none" />
                </div>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-white uppercase tracking-wider pl-1">Nama Bisnis</label>
                    <input type="text" placeholder="Contoh: Kopi Senja" className="w-full bg-background-dark border border-white/10 rounded-xl px-4 py-4 text-white focus:border-primary focus:ring-1 focus:ring-primary transition-all outline-none" />
                  </div>
                  <div className="space-y-2">
                    <label className="text-sm font-bold text-white uppercase tracking-wider pl-1">Tipe Bisnis</label>
                    <select className="w-full bg-background-dark border border-white/10 rounded-xl px-4 py-4 text-white focus:border-primary focus:ring-1 focus:ring-primary transition-all outline-none appearance-none">
                      <option>Cafe / Resto</option>
                      <option>Retail / Toko</option>
                      <option>Toko Kelontong</option>
                      <option>Jasa / Properti</option>
                    </select>
                  </div>
                </div>
                <div className="space-y-2">
                  <label className="text-sm font-bold text-white uppercase tracking-wider pl-1">Pesan / Kebutuhan</label>
                  <textarea rows="4" placeholder="Ceritakan kebutuhan bisnis Anda..." className="w-full bg-background-dark border border-white/10 rounded-xl px-4 py-4 text-white focus:border-primary focus:ring-1 focus:ring-primary transition-all outline-none resize-none"></textarea>
                </div>
                <button type="button" className="w-full bg-primary hover:bg-primary/90 text-background-dark font-black py-5 rounded-2xl text-lg transition-all shadow-xl shadow-primary/20">
                  Kirim Permintaan Demo
                </button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </Layout>
  )
}

export default ContactPage
