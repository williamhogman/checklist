#
# Cookbook Name:: scala-sbt
# Recipe:: default
#

include_recipe "java"
include_recipe "apt" if platform?("debian","ubuntu")
include_recipe "yum" if platform?("redhat", "centos", "scientific", "fedora", "arch", "suse")

if platform?("redhat", "centos", "scientific", "fedora", "arch", "suse")
  yum_repository "sbt" do
    name "typesafe"
    url node[:scala_sbt][:repo_url][:redhat]
    action :add
  end
else
  apt_repository "typesafe" do
    uri "http://apt.typesafe.com/"
    distribution "unicorn"
    components ["main"]
    key "typesafe-repo-public.asc"
    notifies :run, "execute[apt-get update]", :immediately
  end
end

package "sbt"
